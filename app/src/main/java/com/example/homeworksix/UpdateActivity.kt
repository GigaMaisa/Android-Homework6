package com.example.homeworksix

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.example.homeworksix.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity(), BaseSteps {
    private lateinit var binding: ActivityUpdateBinding
    private lateinit var firstNameEditText: AppCompatEditText
    private lateinit var lastNameEditText: AppCompatEditText
    private lateinit var emailEditText: AppCompatEditText
    private lateinit var ageEditText: AppCompatEditText
    private lateinit var updateButton: AppCompatButton

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user: User? = getUserFromMainActivity()

        with(binding) {
            firstNameEditText = etFirstName
            lastNameEditText = etLastName
            emailEditText = etEmail
            ageEditText = etAge
            updateButton = btnUpdateUser
        }

        user?.run {
            firstNameEditText.setText(firstName)
            lastNameEditText.setText(lastName)
            emailEditText.setText(email)
            ageEditText.setText(age.toString())
        }

        updateButton.setOnClickListener {
            validateFields(firstNameEditText, lastNameEditText, emailEditText, ageEditText, context = this)
            val firstName: String? = getString(firstNameEditText)
            val lastName: String? = getString(lastNameEditText)
            val email: String? = getEmail(emailEditText, this)
            val age: Int? = getAge(ageEditText, this)

            firstName?.let {
                lastName?.let {
                    email?.let {
                        age?.let {
                            setOfUsers.remove(user)
                            setOfUsers.add(User(firstName, lastName, email, age))
                            finish()
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getUserFromMainActivity(): User? = intent.getParcelableExtra("userData", User::class.java)

    fun onBack(view: View) {
        finish()
    }
}