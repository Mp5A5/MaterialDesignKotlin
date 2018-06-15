package www.mp5a5.com.materialdesignkotlin

import android.os.Parcel
import android.os.Parcelable

/**
 * @describe
 * @author ：king9999 on 2018/6/15 09：54
 * @email：wwb199055@enn.cn
 */
data class WeaponEntity(var id: Int, var name: String, var content: String, var imageUrl: String, var symbol: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(content)
        parcel.writeString(imageUrl)
        parcel.writeString(symbol)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeaponEntity> {
        override fun createFromParcel(parcel: Parcel): WeaponEntity {
            return WeaponEntity(parcel)
        }

        override fun newArray(size: Int): Array<WeaponEntity?> {
            return arrayOfNulls(size)
        }
    }
}

data class WeaponResult(var status: Int, var desc: String, var data: List<WeaponEntity>)