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

    var translateText = MutableLiveData<String>("")
    fun getSymbol(view: View) {
        getAns = false
        val btn = view as Button
        _currentExpression += btn.text.toString()
        translateText.value += btn.text.toString()
    }
    fun getNumber(view: View) {
        if(getAns) {
            clearText()
            getAns = false
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

    fun clearText() {
        translateText.value = ""
        _currentExpression = ""
    }
}