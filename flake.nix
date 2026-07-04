{
  description = "jOOQ MCVE development environment (JDK + Testcontainers/podman wiring)";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";
  };

  outputs = { self, nixpkgs }:
    let
      supportedSystems = [ "x86_64-linux" "aarch64-linux" "x86_64-darwin" "aarch64-darwin" ];
      forAllSystems = nixpkgs.lib.genAttrs supportedSystems;
    in
    {
      devShells = forAllSystems (system:
        let
          pkgs = import nixpkgs {
            inherit system;
            config.allowUnfree = true;
          };
          isDarwin = pkgs.stdenv.isDarwin;

          # Gradle 8.13 (see gradle/wrapper/gradle-wrapper.properties) runs on
          # Java 8-23; jOOQ 3.21 needs Java 17+. JDK 21 (LTS) satisfies both and
          # is the toolchain the codegen/tests build against.
          jdk = pkgs.jdk21_headless;

          nixPodman = "${pkgs.podman}/bin/podman";
        in
        {
          default = pkgs.mkShell {
            packages = with pkgs; [
              jdk
              git
            ] ++ pkgs.lib.optionals (!isDarwin) [
              # Container CLI (the daemon is system-level via podman). On macOS
              # podman runs in a VM installed outside nix (`podman machine`).
              podman
            ];

            env = {
              JAVA_HOME = "${jdk}";
              # Testcontainers' Ryuk resource-reaper is flaky under rootless
              # podman (privileged socket-mount). The gradle tc-stop tasks clean
              # up containers explicitly, so disable it. See
              # https://java.testcontainers.org/features/configuration/
              TESTCONTAINERS_RYUK_DISABLED = "true";
            };

            shellHook = ''
              # Testcontainers talks to the container runtime via DOCKER_HOST.
              # Point it at podman's socket only when podman's daemon is actually
              # reachable; otherwise leave DOCKER_HOST alone so a running Docker
              # Desktop / dockerd is used instead. We do NOT key off "docker is
              # down" — Docker users often enter the shell before Docker starts,
              # and exporting a dead podman socket would break their later
              # `docker` calls.
              if ${nixPodman} info >/dev/null 2>&1; then
                ${if isDarwin
                  then ''export DOCKER_HOST="unix://$HOME/.local/share/containers/podman/machine/podman.sock"''
                  else ''export DOCKER_HOST="unix:///run/user/$(id -u)/podman/podman.sock"''
                }
                echo "jOOQ-mcve dev shell: DOCKER_HOST -> $DOCKER_HOST (podman)"
              else
                echo "jOOQ-mcve dev shell: podman not reachable, leaving DOCKER_HOST to docker default"
                ${pkgs.lib.optionalString isDarwin ''echo "  (run 'podman machine start' to bring podman up)"''}
              fi

              echo "jOOQ-mcve dev shell loaded ($(java -version 2>&1 | head -1))"
            '';
          };
        }
      );
    };
}
