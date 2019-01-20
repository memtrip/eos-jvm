package com.memtrip.eos.http.rpc.test

import java.lang.NumberFormatException

interface ExpressionConverter {
    fun rpnInput(operation: String): String
    fun findOperands(parts:  List<String>): List<String>
    fun findOperators(parts: List<String>): List<String>
    fun unaryOperation(parts: List<String>): List<UnaryOperation>
}

interface Operation

interface UnaryOperation {
    fun value(): String
}

class DefaultExpressionConverter: ExpressionConverter {

    override fun unaryOperation(parts: List<String>): List<UnaryOperation> {
        for (parts in 0 until parts.size) {
            if (parts)
        }
    }

    private fun isOperand(value: String): Boolean {
        try {
            Integer.parseInt(value)
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    override fun findOperands(parts: List<String>): List<String> {
        return parts.filter { part ->
            try {
                Integer.parseInt(part)
                true
            } catch (e: NumberFormatException) {
                false
            }
        }
    }

    override fun findOperators(parts: List<String>): List<String> {
        return parts.filter { part ->
            try {
                (part == "+") ||
                        (part == "-") ||
                        (part == "++") ||
                        (part == "--") ||
                        (part == "/") ||
                        (part == "*")
            } catch (e: NumberFormatException) {
                false
            }
        }
    }

    override fun rpnInput(operation: String): String {

        val parts = operation.split(" ")
        val operands = findOperands(parts)
        val operators = findOperators(parts)

        if (operators.contains("1 ++")) {
            return "1++"
        }
    }

    private fun unaryOperator() {

    }
}