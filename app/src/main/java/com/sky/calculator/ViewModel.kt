package com.sky.calculator

import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
            translateText.value = ""
            _currentExpression = ""
            getAns = false
        }
        val btn = view as Button
        _currentExpression += btn.text.toString()
        translateText.value += btn.text.toString()
    }
}