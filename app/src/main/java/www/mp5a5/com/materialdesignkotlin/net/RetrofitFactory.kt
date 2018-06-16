package www.mp5a5.com.materialdesignkotlin.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import www.mp5a5.com.materialdesignkotlin.BuildConfig
import java.util.concurrent.TimeUnit

/**
 * @describe
 * @author ：king9999 on 2018/6/15 14：11
 * @email：wwb199055@enn.cn
 */
object RetrofitFactory {
    
    init {
        init()
    }
    
    private var retrofit: Retrofit? = null
    
    private fun init() {
        
        val mBuilder = OkHttpClient.Builder()
                .connectTimeout(9, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
        // 如果为 debug 模式，则添加日志拦截器
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            mBuilder!!.addInterceptor(interceptor)
        }
        
        retrofit = Retrofit.Builder()
                .baseUrl(BeautifulGirlUrl.BASE_ULR)
                .client(mBuilder!!.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
    
    
    fun <T> create(clazz: Class<T>): T {
        return retrofit!!.create(clazz)
    }
}