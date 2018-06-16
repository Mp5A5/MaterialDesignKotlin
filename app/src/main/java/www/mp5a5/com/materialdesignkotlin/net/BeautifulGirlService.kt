package www.mp5a5.com.materialdesignkotlin.net

import io.reactivex.Observable

/**
 * @describe
 * @author ：king9999 on 2018/6/16 12：25
 * @email：wwb199055@enn.cn
 */
object BeautifulGirlService : BaseBeautifulGirlService() {
    
    private val mBeautifulGirlUrl: BeautifulGirlUrl = RetrofitFactory.create(BeautifulGirlUrl::class.java)
    
    fun getBeautifulGirlData(type: Int, page: Int): Observable<WeaponEntityResult> {
        val params = getPublicParams()
        //val params = mutableMapOf<String, Any>()
        //val params: MutableMap<String, Any> = mutableMapOf()
        params.put("type", type.toString())
        params.put("num", "20")
        params.put("page", page.toString())
        return mBeautifulGirlUrl!!.beautifulGirlEntity(params)
    }
}