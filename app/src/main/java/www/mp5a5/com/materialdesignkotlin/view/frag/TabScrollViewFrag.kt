package www.mp5a5.com.materialdesignkotlin.view.frag

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_tab.*
import www.mp5a5.com.materialdesignkotlin.R

/**
 * @describe
 * @author ：king9999 on 2018/6/19 17：49
 * @email：wwb199055@enn.cn
 */
class TabScrollViewFrag : Fragment() {
    
    private lateinit var mMsg: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMsg = arguments!!.getString("msg")
    }
    
    companion object {
        fun newInstance(msg: String): TabScrollViewFrag {
            val fragment = TabScrollViewFrag()
            val bundle = Bundle()
            bundle.putString("msg", msg)
            fragment.arguments = bundle
            return fragment
        }
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTabTxt.text = mMsg
        mTabTxt.setOnClickListener {
            startActivity(Intent(activity, TabScrollViewActivity::class.java))
        }
    }
    
    
}