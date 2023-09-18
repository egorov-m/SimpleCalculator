package com.example.simplecalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var expression: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        expression = findViewById(R.id.expression)
        val keyboard = findViewById<GridLayout>(R.id.keyboard)
        for (k in keyboard.children) {
            if (k is Button) {
                k.setOnClickListener(this)
            }
        }
    }

    override fun onClick(view: View?) {
        val button = view as? Button
        button ?: return
        when (val buttonValue = button.text.toString()) {
            "C" -> {
                expression?.text = "0"
            }
            "«" -> {
                val exp = expression?.text.toString()
                if (exp.length > 1) {
                    expression?.text = exp.substring(0, exp.length - 1)
                } else {
                    expression?.text = "0"
                }
            }
            "=" -> {
                val exp = Expression(expression?.text.toString())
                try {
                    expression?.text = exp.calculate().toString()
                } catch (_: RuntimeException) { }
            }
            else -> {
                expression?.text = expression?.text.toString() + buttonValue
            }
        }
    }
}
