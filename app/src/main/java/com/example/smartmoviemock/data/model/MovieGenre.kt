package com.example.smartmoviemock.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MovieGenre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    val genreImagePath: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieGenre> {
        override fun createFromParcel(parcel: Parcel): MovieGenre {
            return MovieGenre(parcel)
        }

        override fun newArray(size: Int): Array<MovieGenre?> {
            return arrayOfNulls(size)
        }
    }
}