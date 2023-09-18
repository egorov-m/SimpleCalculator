package com.example.simplecalculator

import java.util.Stack

class Expression(exp: String) {
    private val expression = exp

    fun calculate(): Double {
        val operators = Stack<Char>()
        val operands = Stack<Double>()
        var i = 0
        while (i < expression.length) {
            val token = expression[i]
            when {
                token.isDigit() -> {
                    var j = i
                    while (j < expression.length && (expression[j].isDigit() || expression[j] == '.')) {
                        j++
                    }
                    operands.push(expression.substring(i, j).toDouble())
                    i = j - 1
                }
                token == '(' -> operators.push(token)
                token == ')' -> {
                    while (operators.peek() != '(') {
                        operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()))
                    }
                    operators.pop()
                }
                token in "+-*/" -> {
                    while (!operators.empty() && hasPrecedence(token, operators.peek())) {
                        operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()))
                    }
                    operators.push(token)
                }
            }
            i++
        }
        while (!operators.empty()) {
            operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()))
        }
        return operands.pop()
    }

    private fun hasPrecedence(op1: Char, op2: Char): Boolean {
        if (op2 == '(' || op2 == ')') return false
        return (op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')
    }

    private fun applyOperator(operator: Char, b: Double, a: Double): Double {
        return when (operator) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> a / b
            else -> throw IllegalArgumentException("Invalid operator")
        }
    }
}
