package www.mp5a5.com.materialdesignkotlin

import io.reactivex.Observable
import retrofit2.http.GET

/**
 * @describe
 * @author ：king9999 on 2018/6/15 13：31
 * @email：wwb199055@enn.cn
 */
interface MyService {

    @get:GET("weaponInfo")
    val veaponInfo: Observable<WeaponResult>

}