package www.mp5a5.com.materialdesignkotlin.view.frag

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_none.*
import www.mp5a5.com.materialdesignkotlin.R
import www.mp5a5.com.materialdesignkotlin.view.act.BottomSheetActivity
import www.mp5a5.com.materialdesignkotlin.view.act.BottomSheetDialogActivity
import www.mp5a5.com.materialdesignkotlin.view.act.BottomSheetDialogFragmentAct

/**
 * @describe
 * @author ：king9999 on 2018/6/19 17：49
 * @email：wwb199055@enn.cn
 */
class NoneFragment : Fragment() {
    
    private lateinit var mMsg: String
    private var mBottomSheetBehavior: BottomSheetBehavior<RecyclerView>? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMsg = arguments!!.getString("msg")
    }
    
    companion object {
        fun newInstance(msg: String): NoneFragment {
            val fragment = NoneFragment()
            val bundle = Bundle()
            bundle.putString("msg", msg)
            fragment.arguments = bundle
            return fragment
        }
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_none, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        
        super.onViewCreated(view, savedInstanceState)
        btBottomSheet.setOnClickListener {
            startActivity(Intent(activity, BottomSheetActivity::class.java))
        }
        
        btBottomSheetDialog.setOnClickListener {
            startActivity(Intent(activity, BottomSheetDialogActivity::class.java))
        }
        
        btnBottomSheetFragment.setOnClickListener {
            startActivity(Intent(activity,BottomSheetDialogFragmentAct::class.java))
        }
        
        
    }
    
    
}