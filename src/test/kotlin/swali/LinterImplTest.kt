package swali

import io.swagger.models.Swagger
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class LinterImplTest {
    @Test
    fun `positive case`() {
        val content = getResourceContent("no_must_violations.yaml")
        val violation1 = Violation("rule1", "desc1", ViolationType.MUST, "link1", listOf("pathA1", "pathB1"))
        val violation2 = Violation("rule2", "desc2", ViolationType.SHOULD, "link2", listOf())
        val rule1 = object : Rule {
            override val id = "id1"

            override fun validate(swagger: Swagger): Violation? {
                assertEquals("API Repository API", swagger.info.title)
                return violation1
            }
        }
        val ruleIgnore = object : Rule {
            override val id = "id_ignore"

            override fun validate(swagger: Swagger): Violation? = throw RuntimeException("This rule should be ignored")
        }
        val rule2 = object : Rule {
            override val id = "id2"

            override fun validate(swagger: Swagger): Violation? = violation2
        }
        val unit = LinterImpl(listOf(rule1, ruleIgnore, rule2))
        val response = unit.doLint(content, setOf("id_ignore"))
        assertEquals(listOf(violation1, violation2), response.violations)
        assertEquals(mapOf(ViolationType.MUST to 1, ViolationType.SHOULD to 1), response.violationsCount)
    }

    @Test
    fun `negative case`() {
        val rule1 = object : Rule {
            override val id = "id1"

            override fun validate(swagger: Swagger): Violation? {
                fail()
                return null
            }
        }
        val unit = LinterImpl(listOf(rule1))
        val response = unit.doLint("invalid swagger content", setOf("id_ignore"))
        assertEquals(listOf(invalidApiViolation), response.violations)
        assertEquals(mapOf(ViolationType.MUST to 1), response.violationsCount)
    }
}