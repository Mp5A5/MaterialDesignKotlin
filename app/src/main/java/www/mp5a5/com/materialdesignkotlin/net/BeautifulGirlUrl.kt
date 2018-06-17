package www.mp5a5.com.materialdesignkotlin.net

import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * @describe
 * @author ：king9999 on 2018/6/15 13：31
 * @email：wwb199055@enn.cn
 */

interface BeautifulGirlUrl {
    
    companion object {
        const val BASE_ULR: String = "http://route.showapi.com/"
        const val APPID = "45578"
        const val SECRET = "4e6e7a13e16a42059a75a9a8931a779f"
        const val AUTH = "&showapi_sign=$SECRET&showapi_appid=$APPID"
    }
    
    @POST("819-1/")
    fun beautifulGirlEntity(@QueryMap maps: Map<String, String>): Observable<GirlEntityResult>
}