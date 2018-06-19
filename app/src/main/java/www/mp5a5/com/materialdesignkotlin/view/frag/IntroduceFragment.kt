package www.mp5a5.com.materialdesignkotlin.view.frag

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_introduce.*
import kotlinx.android.synthetic.main.item_intro_ali_pay.*
import kotlinx.android.synthetic.main.item_intro_toolbar_expand.*
import www.mp5a5.com.materialdesignkotlin.R

/**
 * @describe
 * @author ：king9999 on 2018/6/14 19：38
 * @email：wwb199055@enn.cn
 */
class IntroduceFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {
    
    private lateinit var mContent: String
    private var mMaskColor: Int = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContent = arguments!!.getString("msg")
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_introduce, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMaskColor = ContextCompat.getColor(this!!.activity!!, R.color.blue_1983D1)
        mIntroAppBarLayoutAl.addOnOffsetChangedListener(this)
    }
    
    companion object {
        fun newInstance(msg: String): IntroduceFragment {
            val fragment = IntroduceFragment()
            val bundle = Bundle()
            bundle.putString("msg", msg)
            fragment.arguments = bundle
            return fragment
        }
    }
    
    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val offSet = Math.abs(verticalOffset)
        val totalScrollRange = appBarLayout!!.totalScrollRange
        val alphaIn = offSet
        val alphaOut = if (200 - offSet < 0) 0 else 200 - offSet
        val maskColorIn = Color.argb(alphaIn, Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor))
        val maskColorInDouble = Color.argb(alphaIn * 2, Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor))
        val maskColorOut = Color.argb(alphaOut * 2, Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor))
        if (offSet <= totalScrollRange / 2) {
            mIntroExpand.visibility = View.VISIBLE
            mIntroCollapse.visibility = View.GONE
            mExpandMaskV.setBackgroundColor(maskColorInDouble)
        }else{
            mIntroExpand.visibility = View.GONE
            mIntroCollapse.visibility = View.VISIBLE
            mExpandMaskV.setBackgroundColor(maskColorOut)
        }
        v_pay_mask.setBackgroundColor(maskColorIn)
    }
    
    
}