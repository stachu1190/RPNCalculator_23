package com.example.rpncalculator_23

import android.util.Log
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * a class that implements the Reverse Polish Notation stack and lets you perform operations on it
 */
class RPNStack () {
    private var array: Array<String> = arrayOf("")
    private var history: MutableList<Array<String>> = mutableListOf<Array<String>>()
    private var stackSize: Int = 1
    private var historySize: Int = 0
    private var decimalPoint: Int = 1

    fun setDecimalPoint(digits: Int) {
        decimalPoint = digits
    }
    fun pushNumber(num: String) {
        if(stackSize > 0)
            array[stackSize-1] += num
    }
    private fun addToHistory() {
        history+=array.clone()
        historySize++
    }
    private fun deleteFromHistory() {
        history.removeAt(historySize-1)
        historySize--
    }
    fun undo() {
        if(history.size >= 2) {
            array = history[historySize-1]
            stackSize = array.size
            deleteFromHistory()
        }
    }
    private fun printStack() {
        for (i in stackSize-1 downTo 0) {
            println("${i+1}:    ${array[i]}")
        }
        /*for (i in 0 until history.size)
        {
            println(history[i].joinToString())
        }*/
    }
    fun newNumber() {
        if(array[stackSize-1] != "" && array[stackSize-1] != "0.") {
            addToHistory()
            stackSize++
            array += ""
        }
    }
    fun addDot() {
        if(!array[stackSize-1].contains(".")) {
            if(array[stackSize-1] == "")
                array[stackSize-1] = "0."
            else
                array[stackSize-1] += "."
        }
    }
    fun changeSign() {
        if(array[stackSize-1] != "" && array[stackSize-1] != "0" && array[stackSize-1] != "0.") {
            addToHistory()
            if(array[stackSize-1].get(0) == '-')
                array[stackSize-1] = array[stackSize-1].drop(1)
            else
                array[stackSize-1] = "-" + array[stackSize-1]
        }
    }
    fun getStack(): Array<String> {
        var returnStack: Array<String> = arrayOf()
        for (i in 1..stackSize) {
            if(array[i-1] != "" && array[i-1].toFloat() % 1.0 != 0.0)
                returnStack += "${i}: " + String.format("%.${decimalPoint}f", array[i-1].toFloat())
            else
                returnStack += "${i}: " + array[i-1]
        }
        returnStack.reverse()
        return returnStack
    }
    private fun checkTwoOnTop() : Boolean { // checks if the conditions for correct mathematical operations are met
        return stackSize > 1 && array[stackSize-1] != "" && array[stackSize-1]!= "0."
    }
    fun add(){
        if(checkTwoOnTop()) {
            addToHistory()
            var value = array[stackSize-1].toFloat() + array[stackSize-2].toFloat()
            var toString: String
            if(value % 1.0 == 0.0)
                toString = value.toInt().toString()
            else
                toString = value.toString()
            array = dropLastItem(array)
            array[stackSize-2] = toString
            stackSize--
        }

    }
    fun subtract(){
        if(checkTwoOnTop()) {
            addToHistory()
            var value = array[stackSize-2].toFloat() - array[stackSize-1].toFloat()
            var toString: String
            if(value % 1.0 == 0.0)
                toString = value.toInt().toString()
            else
                toString = value.toString()
            array = dropLastItem(array)
            array[stackSize-2] = toString
            stackSize--
        }

    }
    fun divide(){
        if(checkTwoOnTop() && array[stackSize-1] != "0") {
            addToHistory()
            var value = array[stackSize-2].toFloat() / array[stackSize-1].toFloat()
            var toString: String
            if(value % 1.0 == 0.0)
                toString = value.toInt().toString()
            else
                toString = value.toString()
            array = dropLastItem(array)
            array[stackSize-2] = toString
            stackSize--
        }

    }
    fun multiply(){
        if(checkTwoOnTop()) {
            addToHistory()
            var value = array[stackSize-2].toFloat() * array[stackSize-1].toFloat()
            var toString: String
            if(value % 1.0 == 0.0)
                toString = value.toInt().toString()
            else
                toString = value.toString()
            array = dropLastItem(array)
            array[stackSize-2] = toString
            stackSize--
        }

    }
    fun power(){
        if(checkTwoOnTop()) {
            addToHistory()
            var value = array[stackSize-2].toFloat().pow(array[stackSize-1].toFloat())
            if(value.isNaN()) {
                //Toast.makeText(context, "The result is not a number", Toast.LENGTH_SHORT)
                return
            }
            var toString: String
            if(value % 1.0 == 0.0)
                toString = value.toInt().toString()
            else
                toString = value.toString()
            array = dropLastItem(array)
            array[stackSize-2] = toString
            stackSize--
        }

    }
    private fun dropLastItem(array: Array<String>): Array<String> {
        val result = array.toMutableList()
        result.removeAt(stackSize-1)
        return result.toTypedArray()
    }
    fun sqrt(){
        if(array[stackSize-1] != "" && array[stackSize-1] != "0.") {
            addToHistory()
            var value = sqrt(array[stackSize-1].toFloat())
            if(value.isNaN()) {
                //Toast.makeText(context, "The result is not a number", Toast.LENGTH_SHORT)
                return
            }
            var toString: String
            if(value % 1.0 == 0.0)
                toString = value.toInt().toString()
            else
                toString = value.toString()
            array[stackSize-1] = toString
        }

    }
    fun ac() {
        addToHistory()
        array = arrayOf("")
        stackSize = 1
    }
    fun drop() {
        addToHistory()
        array = dropLastItem(array)
        stackSize--
        if(stackSize == 0) {
            array = arrayOf("")
            stackSize = 1
        }
    }
    fun swap() {
        if(checkTwoOnTop()) {
            addToHistory()
            val temp = array[stackSize-1]
            array[stackSize-1] = array[stackSize-2]
            array[stackSize-2] = temp
        }
    }
}