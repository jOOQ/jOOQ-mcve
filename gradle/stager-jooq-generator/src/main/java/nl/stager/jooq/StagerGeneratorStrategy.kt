package nl.stager.jooq

import java.util.*
import java.util.regex.Pattern
import org.jooq.SQLDialect
import org.jooq.codegen.DefaultGeneratorStrategy
import org.jooq.codegen.GeneratorStrategy
import org.jooq.meta.CatalogDefinition
import org.jooq.meta.DefaultInverseForeignKeyDefinition
import org.jooq.meta.DefaultUniqueKeyDefinition
import org.jooq.meta.Definition
import org.jooq.meta.ForeignKeyDefinition
import org.jooq.meta.IdentityDefinition
import org.jooq.meta.IndexDefinition
import org.jooq.meta.ManyToManyKeyDefinition
import org.jooq.meta.SchemaDefinition
import org.jooq.meta.TableDefinition


private val idRegex = "_id".toRegex()
private val underscoreRegex = "_".toRegex()
private val columnNameRegex = Pattern.compile("(?<=[a-z])[A-Z]")

/** Our custom generator strategy for name conversions. */
@Suppress("unused") // Used by the jOOQ Gradle plugin
class StagerGeneratorStrategy : DefaultGeneratorStrategy() {

  /** Despite the name this is also used when generating Kotlin code. */
  override fun getJavaClassName(definition: Definition, mode: GeneratorStrategy.Mode): String =
    composeClassName(definition, mode) // Our table names need little changes to be class names

  /** Despite the name this is also used when generating Kotlin code. */
  override fun getJavaMethodName(definition: Definition, mode: GeneratorStrategy.Mode): String {
    if (definition.outputName.contains("eventinfo", ignoreCase = true)) {
      println("${definition.outputName} ${definition.name} ${definition.inputName} ${definition::class.java}")
    }
    when (definition) {
      is ManyToManyKeyDefinition -> {
        val t1: TableDefinition = definition.foreignKey1.referencedTable
        val t2: TableDefinition = definition.foreignKey2.referencedTable
        if (t1.getManyToManyKeys(t2).size == 1) return super.getJavaMethodName(t2, mode)
        return "call" + super.getJavaMethodName(definition, mode)
      }
    }
    return super.getJavaMethodName(definition, mode)
    // return composeClassName(definition, mode) // Our table names need little changes to be class names
  }

  /** Despite the name this is also used when generating Kotlin code. */
  override fun getJavaIdentifier(definition: Definition): String {
    // [#1473] Identity identifiers should not be renamed by custom strategies
    when (definition) {
      is IdentityDefinition -> return "IDENTITY_${getJavaIdentifier(definition.column.container)}"
      is CatalogDefinition -> if (definition.isDefaultCatalog) return "DEFAULT_CATALOG"
      is SchemaDefinition -> if (definition.isDefaultSchema) return "DEFAULT_SCHEMA"

      is ForeignKeyDefinition,
      is DefaultInverseForeignKeyDefinition,
      is DefaultUniqueKeyDefinition -> {
        val uppercaseUnderscoredNameParts = definition.outputName
          .replace(idRegex, "Id")
          .split('_')
          .map { sanitizedNamePart -> uppercaseUnderscored(sanitizedNamePart) }
        return uppercaseUnderscoredNameParts.joinToString("_").uppercase(Locale.getDefault())
        // return definition.outputName.uppercase(Locale.getDefault())
      }

      is IndexDefinition -> {
        if (listOf(SQLDialect.MYSQL, SQLDialect.MARIADB).contains(definition.getDatabase().dialect.family())) {
          val tablePart = definition.table.outputName.uppercase(Locale.getDefault())
          val indexPart = definition.getOutputName().uppercase(Locale.getDefault())
          return "${tablePart}_$indexPart"
        }
      }

      else -> {} // do nothing here as there are some cases in this when-statement that only return "if".
    }

    // https://github.com/jOOQ/jOOQ/blob/93ece30335883779a0519086be735a94eaced022/jOOQ-codegen/src/main/java/org/jooq/codegen/DefaultGeneratorStrategy.java#L132
    // Make our ugly "*_id" column name bits pretty.
    val sanitizedName = definition.outputName
      .replace(idRegex, "Id")
      .replace(underscoreRegex, "__")
    return uppercaseUnderscored(sanitizedName)
  }

  private fun uppercaseUnderscored(sanitizedName: String): String {
    val m = columnNameRegex.matcher(sanitizedName)
    return buildString {
      while (m.find()) m.appendReplacement(this, "_" + m.group().lowercase(Locale.getDefault()))
      m.appendTail(this)
    }.uppercase(Locale.getDefault())
  }

  /** Despite the name this is also used when generating Kotlin code. */
  override fun getJavaMemberName(definition: Definition, mode: GeneratorStrategy.Mode): String =
    outputNameWithCamelCaseId(definition)

  private fun adjustGetterSetterSuffix(definition: Definition): String =
    capitalize(outputNameWithCamelCaseId(definition))

  private fun outputNameWithCamelCaseId(definition: Definition): String =
    definition.outputName.replace(idRegex, "Id")

  companion object {
    private fun composeClassName(definition: Definition, mode: GeneratorStrategy.Mode): String {
      // NOTE(cies): adapted from code found here
      // https://github.com/jOOQ/jOOQ/blob/main/jOOQ-codegen/src/main/java/org/jooq/codegen/DefaultGeneratorStrategy.java
      return buildString {
        // [#4562] Some characters should be treated like underscore
        append(
          capitalize(
            definition
              .outputName
              .replace(' ', '_')
              .replace('-', '_')
              .replace('.', '_')
              .replace(underscoreRegex, "To") // replace underscores (used in join tables) with "To"
          )
        )
        when (mode) {
          GeneratorStrategy.Mode.RECORD -> append("Record")
          GeneratorStrategy.Mode.DAO -> append("Dao")
          GeneratorStrategy.Mode.INTERFACE -> insert(0, "I")
          GeneratorStrategy.Mode.POJO -> append("Pojo")
          else -> append("Tbl") // to differentiate with Hibernate class names
        }
      }
    }

    private fun capitalize(str: String): String =
      if (str.isBlank()) str else str.substring(0, 1).uppercase(Locale.getDefault()) + str.substring(1)
  }
}
