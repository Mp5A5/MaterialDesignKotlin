package www.mp5a5.com.materialdesignkotlin.view.act

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_bottom_sheet_dialog_frag.*
import www.mp5a5.com.materialdesignkotlin.R
import www.mp5a5.com.materialdesignkotlin.view.frag.IBottomSheetFragment

/**
 * @describe
 * @author ：mp5a5 on 2018/8/20 10：06
 * @email：wwb199055@126.com
 */
class BottomSheetDialogFragmentAct : AppCompatActivity() {
    
    private var fragment: IBottomSheetFragment? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet_dialog_frag)
        initListener()
    }
    
    private fun initListener() {
        
        btnOpen.setOnClickListener {
            fragment = IBottomSheetFragment.newInstance()
            fragment!!.show(supportFragmentManager, BottomSheetDialogFragmentAct::class.java.simpleName)
        }
        
        fragment?.behavior?.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.e("BottomSheet", "onSlide=" + slideOffset);
            }
            
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                Log.e("BottomSheet", "newState=" + newState);
            }
            
        })
        
    }
    
    
}


