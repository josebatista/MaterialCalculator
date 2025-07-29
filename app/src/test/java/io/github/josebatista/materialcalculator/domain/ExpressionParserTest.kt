package io.github.josebatista.materialcalculator.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExpressionParserTest {

    private lateinit var parser: ExpressionParser

    @Test
    fun `Simple expression is properly parsed`() {
        // 1. GIVEN
        parser = ExpressionParser("3+5-3x4/3")

        // 2. WHEN (DO SOMETHING WITH WHAT'S GIVEN)
        val actual = parser.parse()

        // 3. THEN (ASSERT EXPECTED == ACTUAL)
        val expected = listOf(
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(5.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(3.0)
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Expression with parenthesis is properly parsed`() {
        parser = ExpressionParser("4-(3x5)")
        val actual = parser.parse()
        val expected = listOf(
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(5.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing)
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Simple expression with floating numbers is properly parsed`() {
        parser = ExpressionParser("3+5-3x4.52/3")
        val actual = parser.parse()
        val expected = listOf(
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(5.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(4.52),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(3.0)
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Expression with parenthesis and floating numbers is properly parsed`() {
        parser = ExpressionParser("4.01-(3x5)")
        val actual = parser.parse()
        val expected = listOf(
            ExpressionPart.Number(4.01),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(5.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing)
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Simple expression with negative numbers is properly parsed`() {
        parser = ExpressionParser("-3+5-3x4.52/3")
        val actual = parser.parse()
        val expected = listOf(
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(5.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(4.52),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(3.0)
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Expression with parenthesis negative and floating numbers is properly parsed`() {
        parser = ExpressionParser("-4.01-(3x5)")
        val actual = parser.parse()
        val expected = listOf(
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(4.01),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(5.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing)
        )
        assertThat(actual).isEqualTo(expected)
    }
}
