package www.mp5a5.com.materialdesignkotlin.net

/**
 * @describe
 * @author ：king9999 on 2018/6/16 12：19
 * @email：wwb199055@enn.cn
 */
open class BaseBeautifulGirlService {
    
    protected fun getPublicParams(): MutableMap<String, String> {
        val map = mutableMapOf<String, String>()
        map.put("showapi_sign", BeautifulGirlUrl.SECRET)
        map.put("showapi_appid", BeautifulGirlUrl.APPID)
        return map
    }
    
    protected fun putParams(map: MutableMap<String, String>, key: String, value: String?) {
        map.put(key, value!!)
    }
    
}