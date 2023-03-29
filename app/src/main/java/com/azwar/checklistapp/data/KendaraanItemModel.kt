package com.azwar.checklistapp.data

import android.os.Parcel
import android.os.Parcelable

data class KendaraanItemModel(
    val id: Int?,
    val itemCompletionStatus: Boolean?,
    val name: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(itemCompletionStatus)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<KendaraanItemModel> {
        override fun createFromParcel(parcel: Parcel): KendaraanItemModel {
            return KendaraanItemModel(parcel)
        }

        override fun newArray(size: Int): Array<KendaraanItemModel?> {
            return arrayOfNulls(size)
        }
    }
}