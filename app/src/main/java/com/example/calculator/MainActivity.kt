package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculator.databinding.MainActivityBinding
import com.example.calculator.ui.theme.CalculatorTheme
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException
import kotlin.math.log


class MainActivity : ComponentActivity() {
    private lateinit var binding: MainActivityBinding
    var lastnumeric =false
    var stateError =false
    var lastDot=false
    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root) }


    fun onAllClearClick(view: View) {

        binding.dataTv.text=""
        binding.resultTv.text=""
        stateError=false
        lastnumeric=false
        lastDot=false
        binding.resultTv.visibility= View.GONE
    }

    fun onEqualClick(view: View) {
        onEqual()
        binding.dataTv.text=binding.resultTv.text.toString().drop(1)    }

    fun onDigitClick(view: View) {

    if (stateError){
        binding.dataTv.text= (view as Button).text
        stateError=false
    }
        else {
            binding.dataTv.append((view as Button).text)
        }
        lastnumeric = true
        onEqual()
    }

    fun onOperatorClick(view: View) {
        if (!stateError && lastnumeric){
            binding.dataTv.append((view as Button).text)
            lastDot=false
            lastnumeric=false
            onEqual()
        }
    }

    fun onBackClick(view: View) {
        binding.dataTv.text= binding.dataTv.text.toString().dropLast(1)
        try {
            val lastchar=binding.dataTv.text.toString().last()
            if (lastchar.isDigit()){
                onEqual()
            }
        }
        catch (e :Exception){
            binding.resultTv.text=""
            binding.resultTv.visibility=View.GONE
            Log.e("last char error ",e.toString())
        }
    }

    fun onClearClick(view: View) {
        binding.dataTv.text=""
        lastnumeric=false
    }
    fun onEqual(){
        if (lastnumeric && !stateError){
            val txt=binding.dataTv.text.toString()
            expression =ExpressionBuilder(txt).build()
            try {
                val result =expression.evaluate()
                binding.resultTv.visibility =View.VISIBLE
                binding.resultTv.text ="="+ result.toString()
            }
            catch (ex:ArithmeticException){
                Log.e("evaluate error",ex.toString())
                binding.resultTv.text="error"
                stateError = true
                lastnumeric=false
            }
        }
    }
}

/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorTheme {
        Greeting("Android")
    }
}*/
