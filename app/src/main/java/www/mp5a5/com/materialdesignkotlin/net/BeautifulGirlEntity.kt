package www.mp5a5.com.materialdesignkotlin.net

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonObject

/**
 * @describe
 * @author ：king9999 on 2018/6/15 09：54
 * @email：wwb199055@enn.cn
 */
data class WeaponEntityResult(val showapi_res_code: Int, val showapi_res_error: String, val showapi_res_body: JsonObject)

data class WeaponEntity(val title: String, val thumb: String, val url: String) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString(), parcel.readString(), parcel.readString()) {
    }
    
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(thumb)
        parcel.writeString(url)
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