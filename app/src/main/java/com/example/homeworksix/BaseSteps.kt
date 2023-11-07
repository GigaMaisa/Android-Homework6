package com.example.homeworksix

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText

interface BaseSteps {
    private fun isEmailValid(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun getAge(viewElement: AppCompatEditText, context: Context): Int? {
        val input: String = viewElement.text.toString()

        if (input.isEmpty()) {
            return null
        }

        return try {
            input.toInt()
        } catch (e: NumberFormatException) {
            showToast("Type valid number", context)
            null
        }
    }

    fun getString(viewElement: AppCompatEditText): String? {
        val input: String = viewElement.text.toString()
        return input.ifEmpty {
            null
        }
    }

    fun validateFields(vararg viewElement: AppCompatEditText, context: Context) {
        for (view in viewElement) {
            val text = view.text.toString()
            if (text.isEmpty()) {
                showToast("Fill all fields", context)
                break
            }
        }
    }

    fun getEmail(viewElement: AppCompatEditText, context: Context): String? {
        val input: String = viewElement.text.toString()
        return if (input.isNotEmpty() && !isEmailValid(input)) {
            showToast("Email is not valid", context)
            null
        } else {
            input
        }
    }

    fun showToast(message: String, context: Context) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}