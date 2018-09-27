package www.mp5a5.com.materialdesignkotlin.view.act

import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bottom_sheet_dialog.*
import www.mp5a5.com.materialdesignkotlin.R

/**
 * @describe
 * @author ：mp5a5 on 2018/8/14 17：38
 * @email：wwb199055@126.com
 */
class BottomSheetDialogActivity : AppCompatActivity() {
    
    private var bottomSheetDialog1: BottomSheetDialog? = null
    private var bottomSheetDialog2: BottomSheetDialog? = null
    private var ivMan: AppCompatImageView? = null
    private var ivWomen: AppCompatImageView? = null
    private val list = mutableListOf<String>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet_dialog)
        intBottomSheetStyle1()
        intBottomSheetStyle2()
        initListener()
    }
    
    
    private fun initListener() {
        
        btnStyle1.setOnClickListener {
            if (!bottomSheetDialog1!!.isShowing) {
                bottomSheetDialog1!!.show()
            }
        }
        
        btnStyle2.setOnClickListener {
            if (!bottomSheetDialog2!!.isShowing) {
                bottomSheetDialog2!!.show()
            }
        }
        
        ivMan?.setOnClickListener {
            bottomSheetDialog1?.dismiss()
        }
        
        ivWomen?.setOnClickListener {
            bottomSheetDialog1?.dismiss()
        }
        
    }
    
    private fun intBottomSheetStyle1() {
        val view = View.inflate(this, R.layout.view_bottom_dialog, null)
        ivMan = view.findViewById<AppCompatImageView>(R.id.ivMan)
        ivWomen = view.findViewById<AppCompatImageView>(R.id.ivWomen)
        bottomSheetDialog1 = BottomSheetDialog(this)
        bottomSheetDialog1!!.setContentView(view)
        bottomSheetDialog1!!.setCanceledOnTouchOutside(true)
    
        /**
         * 设置弹出高度
         */
        /*val mBehavior = BottomSheetBehavior.from(view.parent as View)
        mBehavior.setPeekHeight(height)*/
    }
    
    
    private fun intBottomSheetStyle2() {
    
        for (i in 0..100) {
            list.add("第" + i + "个帅哥")
        }
        
        val recyclerView = LayoutInflater.from(this).inflate(R.layout.view_bottom_sheet_dialog_recycler, null) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyRecyclerViewAdapter(list)
        
        bottomSheetDialog2 = BottomSheetDialog(this)
        bottomSheetDialog2!!.setContentView(recyclerView)
        bottomSheetDialog2!!.setCanceledOnTouchOutside(false)
    }
    
    
    private inner class MyRecyclerViewAdapter(mutableList: MutableList<String>?) : RecyclerView.Adapter<MyRecyclerViewAdapter.RecyclerHolder>() {
        
        var mList = mutableList
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder = RecyclerHolder(layoutInflater.inflate(R.layout.item_recyclerview, parent, false))
        
        override fun getItemCount(): Int = mList!!.size
        
        override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
            holder.txt.text = mList?.get(position)
            holder.txt.setOnClickListener {
                Toast.makeText(this@BottomSheetDialogActivity,"第" + position + "个条目被点击了！",Toast.LENGTH_SHORT).show()
            }
        }
        
        
        private inner class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var txt: TextView = itemView.findViewById(R.id.item_txt) as TextView
        }
        
        
    }
}