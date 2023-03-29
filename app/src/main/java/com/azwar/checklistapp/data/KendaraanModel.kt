package com.azwar.checklistapp.data

import android.os.Parcel
import android.os.Parcelable

data class KendaraanModel(
    val checklistCompletionStatus: Boolean?,
    val id: Int?,
    val items: String?,
    val name: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(checklistCompletionStatus)
        parcel.writeValue(id)
        parcel.writeString(items)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<KendaraanModel> {
        override fun createFromParcel(parcel: Parcel): KendaraanModel {
            return KendaraanModel(parcel)
        }

        override fun newArray(size: Int): Array<KendaraanModel?> {
            return arrayOfNulls(size)
        }
    }
}
