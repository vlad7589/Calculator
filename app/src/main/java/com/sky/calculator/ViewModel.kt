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

    var translateText = MutableLiveData<String>("")
    fun getSymbol(view: View) {
        getAns = false
        val btn = view as Button
        when(btn.text.toString()) {
            "%" -> percent()
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
    }

    fun equals() {
        val exp = ExpressionBuilder(currentExpression).build()
        val res = exp.evaluate()
        getAns = true
        if(res.toInt() - res == 0.0) translateText.value = res.toInt().toString()
        else translateText.value = res.toString()
    }

    fun percent(){
        translateText.value += "%"
        _currentExpression += "/100"
        pressPercent = true
    }

    fun clearText() {
        translateText.value = ""
        _currentExpression = ""
    }
}