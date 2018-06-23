package www.mp5a5.com.materialdesignkotlin.view.frag

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_scroll.*
import www.mp5a5.com.materialdesignkotlin.R

/**
 * @describe
 * @author ：king9999 on 2018/6/14 19：38
 * @email：wwb199055@enn.cn
 */
class AliPayFragment1 : Fragment() {
    
    private lateinit var mMsg: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMsg = arguments!!.getString("msg")
    }
    
    companion object {
        fun newInstance(msg: String): AliPayFragment1 {
            val fragment = AliPayFragment1()
            val bundle = Bundle()
            bundle.putString("msg", msg)
            fragment.arguments = bundle
            return fragment
        }
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scroll, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTabTxt.text = mMsg
        mTabTxt.setOnClickListener {
            startActivity(Intent(activity, AliPayActivity::class.java))
        }
    }
    
    
}