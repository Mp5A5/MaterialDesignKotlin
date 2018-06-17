package www.mp5a5.com.materialdesignkotlin.view.act

import android.R.id
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Explode
import android.transition.Slide
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import www.mp5a5.com.materialdesignkotlin.Constant
import www.mp5a5.com.materialdesignkotlin.R
import www.mp5a5.com.materialdesignkotlin.net.GirlEntity

/**
 * @describe
 * @author ：king9999 on 2018/6/16 16：19
 * @email：wwb199055@enn.cn
 */
class BeautifulGirlDetailActivity : AppCompatActivity() {
    
    private lateinit var girlEntity: GirlEntity
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        // 透明导航栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        // 透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // 设置转场动画
        window.enterTransition = Explode()
        window.exitTransition = Slide()
        setContentView(R.layout.activity_detail)
        intData()
        initView()
    }
    
    private fun intData() {
        girlEntity = intent.getParcelableExtra<GirlEntity>(Constant.GIRL_BEAN)
    }
    
    private fun initView() {
        setSupportActionBar(mDetailToolbarT)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        Glide.with(this).load(girlEntity.thumb).into(mDetailImgIv)
        mDetailToolbarT.title = girlEntity.title
        mDetailTxtTv.text = girlEntity.url
    }
    
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    
    
}