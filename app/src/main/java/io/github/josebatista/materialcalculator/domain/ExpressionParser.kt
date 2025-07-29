package io.github.josebatista.materialcalculator.domain

class ExpressionParser(
    private val calculation: String
) {

    fun parse(): List<ExpressionPart> {
        val result = mutableListOf<ExpressionPart>()
        var i = 0
        while (i < calculation.length) {
            val currentChar = calculation[i]
            when {
                currentChar.isDigit() -> {
                    i = parseNumber(i, result)
                    continue
                }

                currentChar in operationSymbols -> result.add(
                    ExpressionPart.Op(operation = operationFromSymbol(symbol = currentChar))
                )

                currentChar in "()" -> parseParentheses(currentChar, result)
            }
            i++
        }
        return result.toList()
    }

    private fun parseNumber(
        startingIndex: Int,
        result: MutableList<ExpressionPart>
    ): Int {
        var i = startingIndex
        val numberAsString = buildString {
            while (i < calculation.length && calculation[i] in "0123456789.") {
                append(calculation[i])
                i++
            }
        }
        result.add(ExpressionPart.Number(number = numberAsString.toDouble()))
        return i
    }

    private fun parseParentheses(
        currentChar: Char,
        result: MutableList<ExpressionPart>
    ) {
        result.add(
            ExpressionPart.Parentheses(
                type = when (currentChar) {
                    '(' -> ParenthesesType.Opening
                    ')' -> ParenthesesType.Closing
                    else -> throw IllegalStateException("Invalid character")
                }
            )
        )
    }
}
