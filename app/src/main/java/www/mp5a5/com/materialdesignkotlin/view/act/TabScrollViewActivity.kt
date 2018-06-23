package www.mp5a5.com.materialdesignkotlin.view.act

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_tab_scroll_view.*
import www.mp5a5.com.materialdesignkotlin.R
import www.mp5a5.com.materialdesignkotlin.customview.ObservableScrollView

/**
 * @describe
 * @author ：king9999 on 2018/6/19 14：10
 * @email：wwb199055@enn.cn
 */
class TabScrollViewActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener, ObservableScrollView.ScrollViewListener {
    
    //标志位，用来区分是点击了tab还是手动滑动scrollview
    private var tabInterceptTouchEventTag = true
    private var firstAlreadyInflated = true;
    private var currentPosition = 0
    private var firstFloorVg: ViewGroup? = null
    private var secondFloorVg: ViewGroup? = null
    private var thirdFloorVg: ViewGroup? = null
    private var fourthFloorVg: ViewGroup? = null
    private var secondFloorVgPositionDistance: Int = -1; //第二层滑动至顶部的距离
    private var thirdFloorVgPositionDistance: Int = -1; //第3层滑动至顶部的距离
    private var fourthFloorVgPositionDistance: Int = -1; //第4层滑动至顶部的距离
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_tab_scroll_view)
        intView()
        initListener()
    }
    
    
    private fun initListener() {
        mTabWrapFl.setOnTouchListener { v, event ->
            //让tab来处理滑动
            tabInterceptTouchEventTag = true
            return@setOnTouchListener false
        }
        mTabTabLayoutTl.addOnTabSelectedListener(this)
        mTabScrollSv.setScrollViewListener(this)
        mTabScrollSv.setOnTouchListener { v, event ->
            //让scrollview处理滑动
            tabInterceptTouchEventTag = false;
            return@setOnTouchListener false;
        }
    }
    
    private fun intView() {
        
        for (i in 0..3) {
            mTabTabLayoutTl.addTab(mTabTabLayoutTl.newTab().setText("tab" + (i + 1)))
        }
        
        firstFloorVg = LayoutInflater.from(this).inflate(R.layout.item_floor_first, null) as ViewGroup
        secondFloorVg = LayoutInflater.from(this).inflate(R.layout.item_floor_second, null) as ViewGroup
        thirdFloorVg = LayoutInflater.from(this).inflate(R.layout.item_floor_third, null) as ViewGroup
        fourthFloorVg = LayoutInflater.from(this).inflate(R.layout.item_floor_forth, null) as ViewGroup
        
        mTabContainerLl.addView(firstFloorVg)
        mTabContainerLl.addView(secondFloorVg)
        mTabContainerLl.addView(thirdFloorVg)
        mTabContainerLl.addView(fourthFloorVg)
    }
    
    
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //获取各层离screen顶部的位置以及计算滑动值相应顶部所需要的距离
        if (firstAlreadyInflated) {
            firstAlreadyInflated = false
            val firstFloorVgPosition = IntArray(2)
            val secondFloorVgPosition = IntArray(2)
            val thirdFloorVgPosition = IntArray(2)
            val fourthFloorVgPosition = IntArray(2)
            firstFloorVg!!.getLocationOnScreen(firstFloorVgPosition)
            secondFloorVg!!.getLocationOnScreen(secondFloorVgPosition)
            thirdFloorVg!!.getLocationOnScreen(thirdFloorVgPosition)
            fourthFloorVg!!.getLocationOnScreen(fourthFloorVgPosition)
            val firstFloorVgPositionAnchor = firstFloorVgPosition[1]
            val secondFloorVgPositionAnchor = secondFloorVgPosition[1]
            val thirdFloorVgPositionAnchor = thirdFloorVgPosition[1]
            val fourthFloorVgPositionAnchor = fourthFloorVgPosition[1]
            Log.e("-->", "第一层距离屏幕的距离是：" + firstFloorVgPosition[1]);
            Log.e("-->", "第二层距离屏幕的距离是：" + secondFloorVgPosition[1]);
            Log.e("-->", "第三层距离屏幕的距离是：" + thirdFloorVgPosition[1]);
            Log.e("-->", "第四层距离屏幕的距离是：" + fourthFloorVgPosition[1]);
        
            secondFloorVgPositionDistance = secondFloorVgPositionAnchor - firstFloorVgPositionAnchor
            thirdFloorVgPositionDistance = thirdFloorVgPositionAnchor - firstFloorVgPositionAnchor
            fourthFloorVgPositionDistance = fourthFloorVgPositionAnchor - firstFloorVgPositionAnchor
        }
    }
    
    
    override fun onTabReselected(tab: TabLayout.Tab?) {
    }
    
    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }
    
    
    override fun onTabSelected(tab: TabLayout.Tab?) {
        currentPosition = tab!!.position
        //手动滑动页面时则不再次处理滑动
        if (!tabInterceptTouchEventTag) {
            return
        }
        mTabScrollSv.computeScroll()
        when (currentPosition) {
            0 -> mTabScrollSv.smoothScrollTo(0, 0)
            1 -> mTabScrollSv.smoothScrollTo(0, secondFloorVgPositionDistance)
            2 -> mTabScrollSv.smoothScrollTo(0, thirdFloorVgPositionDistance)
            3 -> mTabScrollSv.smoothScrollTo(0, fourthFloorVgPositionDistance)
            else -> throw Exception("Scroll To is rror")
        }
    }
    
    override fun onScrollChanged(scrollView: ObservableScrollView?, x: Int, y: Int, oldx: Int, oldy: Int) {
        
        //让tab来处理滑动
        if (tabInterceptTouchEventTag) {
            return
        }
        if (y < secondFloorVgPositionDistance) {
            if (currentPosition != 0) {
                mTabScrollSv.computeScroll()
                mTabTabLayoutTl.getTabAt(0)!!.select()
            }
        } else if (y < thirdFloorVgPositionDistance) {
            if (currentPosition != 1) {
                mTabScrollSv.computeScroll()
                mTabTabLayoutTl.getTabAt(1)!!.select()
            }
        } else if (y < fourthFloorVgPositionDistance) {
            if (currentPosition != 2) {
                mTabScrollSv.computeScroll()
                mTabTabLayoutTl.getTabAt(2)!!.select()
            }
        } else {
            if (currentPosition != 3) {
                mTabScrollSv.computeScroll()
                mTabTabLayoutTl.getTabAt(3)!!.select()
            }
        }
    }
    
}
