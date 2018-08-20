package www.mp5a5.com.materialdesignkotlin.view.act

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_recyclerview.*
import www.mp5a5.com.materialdesignkotlin.R

/**
 * @describe
 * @author ：mp5a5 on 2018/8/14 10：43
 * @email：wwb199055@126.com
 */
class BottomSheetActivity : AppCompatActivity() {
    
    private val list = mutableListOf<String>()
    private var mBottomSheetBehavior: BottomSheetBehavior<RecyclerView>? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)
        mBottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet1)
        
        for (i in 0..100) {
            list.add("第" + i + "个帅哥")
        }
        
        bottom_sheet1.layoutManager = LinearLayoutManager(this)
        bottom_sheet1.setHasFixedSize(true)
        val adapter = MyRecyclerViewAdapter(list)
        bottom_sheet1.adapter = adapter
        
        
        bt_show.setOnClickListener {
            mBottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        }
        
        bt_hide.setOnClickListener {
            mBottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        }
        
        bt_collapse.setOnClickListener {
            mBottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        
        mBottomSheetBehavior!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("BottomSheet", "onSlide=" + slideOffset);
            }
            
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                Log.e("BottomSheet", "newState=" + newState);
            }
            
        })
    }
    
    
    private inner class MyRecyclerViewAdapter(mutableList: MutableList<String>?) : RecyclerView.Adapter<MyRecyclerViewAdapter.RecyclerHolder>() {
        
        var mList = mutableList
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder = RecyclerHolder(layoutInflater.inflate(R.layout.item_recyclerview, parent, false))
        
        override fun getItemCount(): Int = mList!!.size
        
        override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
            holder.txt.text = mList?.get(position)
        }
        
        
        private inner class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var txt: TextView = itemView.findViewById(R.id.item_txt) as TextView
        }
        
        
    }
}
    
