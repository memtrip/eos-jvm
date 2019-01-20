package com.memtrip.eos.http.rpc.test

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RPNTests {

    private lateinit var expressionConverter: ExpressionConverter

    @Before
    fun setup() {
        expressionConverter = DefaultExpressionConverter()
    }

    @Test
    fun findUnaryOperations() {
        // given
        val parts = listOf("1", "++")

        // when
        val unaryOperators = expressionConverter.unaryOperation(parts)

        // then
        assertEquals(unaryOperators[0], object : UnaryOperation {
            override fun value(): String {
                return "1 ++"
            }
        })
    }

    @Test
    fun findOperands() {
        // given
        val parts = listOf("1", "++")

        // when
        val operands = expressionConverter.findOperands(parts)

        // then
        assertEquals(operands[0], "1")
    }

    @Test
    fun findOperandsInComplexExpression() {
        // given
        val parts = listOf("5", "++", "1", "2", "+", "+")

        // when
        val operands = expressionConverter.findOperands(parts)

        print(operands)

        // then
        assertEquals(operands[0], "5")
        assertEquals(operands[1], "1")
        assertEquals(operands[2], "2")
    }

    @Test
    fun testFindOperators() {
        // given
        val parts = listOf("1", "++")

        // when
        val operators = expressionConverter.findOperators(parts)

        // then
        assertEquals(operators[0], "++")
    }

    @Test
    fun testFindInOperatorsExpression() {
        // given
        val parts = listOf("5", "++", "1", "2", "+", "+")

        // when
        val operators = expressionConverter.findOperators(parts)

        // then
        assertEquals(operators[0], "++")
        assertEquals(operators[1], "+")
    }

    @Test
    fun testFindDivisionOperatorsExpression() {
        // given
        val parts = listOf("5", "/", "1", "2", "*", "+")

        // when
        val operators = expressionConverter.findOperators(parts)

        // then
        assertEquals(operators[0], "/")
        assertEquals(operators[1], "*")
    }

    @Test
    fun testUnaryOperation() {
        // given
        val input = "1 ++"

        // when
        val expresionConverter: ExpressionConverter = DefaultExpressionConverter()
        val result = expresionConverter.rpnInput(input)

        // then
        assertEquals("(1++)", result)
    }

    @Test
    fun testBinaryOperation() {
        // given
        val input = "1 2 +"

        // when
        val expresionConverter: ExpressionConverter = DefaultExpressionConverter()
        val result = expresionConverter.rpnInput(input)

        // then
        assertEquals("(1+2)", result)
    }

    @Test
    fun testUnaryAndBinaryOperation() {
        // given
        val input = "1 2 ++ +"

        // when
        val expresionConverter: ExpressionConverter = DefaultExpressionConverter()
        val result = expresionConverter.rpnInput(input)

        // then
        assertEquals("(1+(2++))", result)
    }
}