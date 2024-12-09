package com.sky.calculator

import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder

class ViewModel : ViewModel() {
    private var _currentExpression = ""
    val currentExpression
        get() = _currentExpression
    var getAns = false
    var pressPercent = false
    var plusMinusOn = false
    var openBrackets = false

    var translateText = MutableLiveData<String>("")
    fun getSymbol(view: View) {
        getAns = false
        plusMinusOn = false
        val btn = view as Button
        when(btn.text.toString()) {
            "%" -> percent()
            "+/-" -> plusMinusBtn()
            else -> {
                _currentExpression += btn.text.toString()
                translateText.value += btn.text.toString()
            }
        }
    }
    fun getNumber(view: View) {
        if(getAns) {
            clearText()
            getAns = false
        } else if (pressPercent) {
            _currentExpression += "*"
            translateText.value += "*"
        }
        val btn = view as Button
        _currentExpression += btn.text.toString()
        translateText.value += btn.text.toString()
        if(translateText.value.toString().contains('(')) openBrackets = true
    }

    fun equals() {
        val parenthesisCount = currentExpression.count { it == '(' } - currentExpression.count { it == ')' }
        try {
            val exp = ExpressionBuilder(currentExpression + ')'.toString().repeat(parenthesisCount)).build()
            val res = exp.evaluate()
            getAns = true
            _currentExpression = res.toString()
            if(res.toInt() - res == 0.0) translateText.value = res.toInt().toString()
            else translateText.value = res.toString()
            pressPercent = false
            if(res < 0) plusMinusOn = true
            else plusMinusOn = false
            openBrackets = false
        } catch (e: Exception){
            clearText()
            getAns = false
        }
    }

    fun percent(){
        translateText.value += "%"
        _currentExpression += "/100"
        pressPercent = true
    }

    fun addBrackets(){
        getAns = false
        if(!openBrackets){
            _currentExpression += '('
            translateText.value += '('
        } else {
            _currentExpression += ')'
            translateText.value += ')'
            openBrackets = false
        }
    }

    fun plusMinusBtn(){
        val indexExp = _currentExpression.indexOfLast { when(it) {
            '+', '-', '*', '/' -> true
            else -> false
        } } + 1
        val indexTrl = translateText.value?.indexOfLast {
            when(it) {
                '+', '-', '*', '/' -> true
                else -> false
            } } as Int + 1
        if(!plusMinusOn) {
            translateText.value = translateText.value?.subSequence(0, indexTrl).toString() +
                    "(-" +
                    translateText.value?.subSequence(indexTrl, translateText.value.toString().length).toString()
            _currentExpression = _currentExpression.subSequence(0, indexExp).toString() +
                    "(-" +
                    _currentExpression.subSequence(indexExp, _currentExpression.length).toString()
            plusMinusOn = true
        } else if(getAns && _currentExpression.toDouble() < 0) {
            translateText.value = translateText.value.toString().removeRange(0,1)
            _currentExpression = _currentExpression.removeRange(0,1)
            plusMinusOn = false
        } else {
            val index = translateText.value.toString().lastIndexOf("(-")
            translateText.value = translateText.value.toString().removeRange(index,index+2)
            _currentExpression = _currentExpression.removeRange(index, index+2)
            plusMinusOn = false
        }
        getAns = false
    }

    fun clearText() {
        translateText.value = ""
        _currentExpression = ""
        plusMinusOn = false
    }
}