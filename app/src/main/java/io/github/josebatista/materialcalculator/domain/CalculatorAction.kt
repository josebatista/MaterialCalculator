package io.github.josebatista.materialcalculator.domain

sealed interface CalculatorAction {
    data class Number(val number: Int) : CalculatorAction
    data class Op(val operation: Operation) : CalculatorAction
    data object Parentheses : CalculatorAction
    data object Clear : CalculatorAction
    data object Delete : CalculatorAction
    data object Decimal : CalculatorAction
    data object Calculate : CalculatorAction
}
