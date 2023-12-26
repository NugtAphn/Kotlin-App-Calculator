package com.nugtaphn.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var txtInput : TextView? = null
    var lastNum : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtInput = findViewById(R.id.txtInput)
    }

    fun onDigit(view : View){
        txtInput?.let{
            txtInput!!.append((view as Button).text)
        }
        lastNum = true
        lastDot = false
    }

    fun onClear(view: View){
        txtInput?.let{
            txtInput!!.text = ""
        }
    }

    fun onDecimalPoint(view: View){
        if(lastNum && !lastDot){
            txtInput?.let{
                txtInput!!.append(".")
            }
            lastNum = false
            lastDot = true
        }
    }

    fun onEqual(view: View){
        if(lastNum){
            var txtValue = txtInput?.text.toString()
            var prefix = ""
            try {
                if(txtValue.startsWith("-")){
                    prefix = "-"
                    txtValue = txtValue.substring(1)
                }
                if(txtValue.contains("-")){
                    val splitValue = txtValue.split("-")
                    var firstValue = splitValue[0]
                    var secondValue = splitValue[1]
                    if(prefix.isNotEmpty()){
                        firstValue += prefix
                    }
                    txtInput?.text = removeZeroAfterDot((firstValue.toDouble() - secondValue.toDouble()).toString())
                }else if(txtValue.contains("+")){
                    val splitValue = txtValue.split("+")
                    var firstValue = splitValue[0]
                    var secondValue = splitValue[1]
                    txtInput?.text = removeZeroAfterDot((firstValue.toDouble() + secondValue.toDouble()).toString())
                }else if(txtValue.contains("*")){
                    val splitValue = txtValue.split("*")
                    var firstValue = splitValue[0]
                    var secondValue = splitValue[1]
                    txtInput?.text = removeZeroAfterDot((firstValue.toDouble() * secondValue.toDouble()).toString())
                }else if(txtValue.contains("/")){
                    val splitValue = txtValue.split("/")
                    var firstValue = splitValue[0]
                    var secondValue = splitValue[1]
                    txtInput?.text = removeZeroAfterDot((firstValue.toDouble() / secondValue.toDouble()).toString())
                }
            }catch (e : ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    fun removeZeroAfterDot(result : String) : String {
        var value = result
        if(value.contains(".0")){
            value = value.substring(0, value.length -2)
        }
        return value
    }

    fun onOperator(view: View) {
        txtInput?.text?.let{
            if(lastNum && !isOperatorAdded(it.toString())) {
                txtInput?.append((view as Button).text)
                lastNum = false
                lastDot = false
            }
        }
    }

    private fun isOperatorAdded(value : String) : Boolean {
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("+") ||
                    value.contains("-") ||
                    value.contains("*") ||
                    value.contains("/") 
        }
    }
}