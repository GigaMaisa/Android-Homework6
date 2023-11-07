package com.example.homeworksix

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.example.homeworksix.databinding.ActivityMainBinding

var setOfUsers: MutableSet<User?> = mutableSetOf()

class MainActivity : AppCompatActivity(), BaseSteps {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firstNameEditText: AppCompatEditText
    private lateinit var lastNameEditText: AppCompatEditText
    private lateinit var emailEditText: AppCompatEditText
    private lateinit var ageEditText: AppCompatEditText
    private lateinit var addUserButton: AppCompatButton
    private lateinit var removeUserButton: AppCompatButton
    private lateinit var updateUserButton: AppCompatButton
    private lateinit var operationStatus: TextView
    private lateinit var totalUsersTextView: TextView
    private lateinit var totalDeletedUsersTextView: TextView
    private var addedUsersCounter: Int = 0
    private var deletedUsersCounter: Int = 0


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            firstNameEditText = etFirstName
            lastNameEditText = etLastName
            emailEditText = etEmail
            ageEditText = etAge
            addUserButton = btnAdd
            removeUserButton = btnRemoveUser
            updateUserButton = btnUpdateUser
            operationStatus = tvOperationStatus
            totalUsersTextView = tvTotalUsers
            totalDeletedUsersTextView = tvDeletedUsers
        }

        addUserButton.setOnClickListener {
            validateFields(firstNameEditText, lastNameEditText, emailEditText, ageEditText, context = this)
            val firstName: String? = getString(firstNameEditText)
            val age: Int? = getAge(ageEditText, this)
            val lastName: String? = getString(lastNameEditText)
            val email: String? = getEmail(emailEditText, this)

            firstName?.let {
                age?.let {
                    lastName?.let {
                        email?.let {
                            val user = User(firstName, lastName, email, age)
                            if (setOfUsers.contains(user)) {
                                changeDesignToError("User already exists")
                            } else {
                                setOfUsers.add(user)
                                changeDesignToSuccess("User added successfully")
                                addedUsersCounter++
                                totalUsersTextView.text = addedUsersCounter.toString()
                            }
                        }
                    }
                }
            }
        }

        removeUserButton.setOnClickListener {
            validateFields(firstNameEditText, lastNameEditText, emailEditText, ageEditText, context = this)
            val firstName: String? = getString(firstNameEditText)
            val age: Int? = getAge(ageEditText, this)
            val lastName: String? = getString(lastNameEditText)
            val email: String? = getEmail(emailEditText, this)

            firstName?.let {
                age?.let {
                    lastName?.let {
                        email?.let {
                            val user = User(firstName, lastName, email, age)

                            if (setOfUsers.contains(user)) {
                                setOfUsers.remove(user)
                                deletedUsersCounter++
                                addedUsersCounter--
                                changeDesignToSuccess("User deleted successfully")
                                totalDeletedUsersTextView.text = deletedUsersCounter.toString()
                                totalUsersTextView.text = addedUsersCounter.toString()
                                clearEditTexts()
                            } else {
                                changeDesignToError("User does not exists")
                            }
                        }
                    }
                }
            }
        }

        updateUserButton.setOnClickListener {
            validateFields(firstNameEditText, lastNameEditText, emailEditText, ageEditText, context = this)
            val firstName: String? = getString(firstNameEditText)
            val lastName: String? = getString(lastNameEditText)
            val email: String? = getEmail(emailEditText, this)
            val age: Int? = getAge(ageEditText, this)

            firstName?.let {
                lastName?.let {
                    email?.let {
                        age?.let {
                            val user = User(firstName, lastName, email, age)
                            if (setOfUsers.contains(user)) {
                                val intent = Intent(this, UpdateActivity::class.java)
                                intent.putExtra("userData", user)
                                startActivity(intent)
                                clearEditTexts()
                            } else {
                                showToast("User doesn't exists, adding user", this)
                                setOfUsers.add(user)
                                addedUsersCounter++
                                totalUsersTextView.text = addedUsersCounter.toString()
                            }
                        }
                    }
                }
            }
            Log.d("SETOFUSERS", "$setOfUsers")
        }
    }

    fun onBack(view: View) {
        finish()
    }

    private fun clearEditTexts() {
        firstNameEditText.text?.clear()
        lastNameEditText.text?.clear()
        emailEditText.text?.clear()
        ageEditText.text?.clear()
    }

    @SuppressLint("SetTextI18n")
    private fun changeDesignToSuccess(message: String) {
        showToast(message, this)
        operationStatus.text = "Success"
        operationStatus.setTextColor(Color.GREEN)
    }

    @SuppressLint("SetTextI18n")
    private fun changeDesignToError(message: String) {
        showToast(message, this)
        operationStatus.text = "Error"
        operationStatus.setTextColor(Color.RED)
    }
}