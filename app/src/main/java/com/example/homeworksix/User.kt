package com.example.homeworksix

import android.os.Parcel
import android.os.Parcelable

data class User(
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val age: Int?
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(email)
        age?.let {
            parcel.writeInt(age)
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt() ?: 0
    )

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel) = User(parcel)
        override fun newArray(size: Int) = arrayOf<User>()
    }

}
