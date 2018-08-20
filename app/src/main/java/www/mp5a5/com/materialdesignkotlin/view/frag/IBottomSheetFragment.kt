package www.mp5a5.com.materialdesignkotlin.view.frag

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_monitor_detail.*
import www.mp5a5.com.materialdesignkotlin.R
import www.mp5a5.com.materialdesignkotlin.customview.BottomSheetFragment

/**
 * @describe
 * @author ：mp5a5 on 2018/8/20 10：36
 * @email：wwb199055@126.com
 */
class IBottomSheetFragment : BottomSheetFragment() {
    
    private val list = mutableListOf<String>()
    
    companion object {
        fun newInstance(): IBottomSheetFragment {
            return IBottomSheetFragment()
        }
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_monitor_detail, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        
        for (i in 0..100) {
            list.add("第" + i + "个帅哥")
        }
        
        rvRecyclerView.setHasFixedSize(true)
        rvRecyclerView.layoutManager = LinearLayoutManager(activity)
        rvRecyclerView.adapter = MyRecyclerViewAdapter(list)
        
        tvClose.setOnClickListener {
            behavior?.state = BottomSheetBehavior.STATE_HIDDEN
        }
        
    }
    
    private inner class MyRecyclerViewAdapter(mutableList: MutableList<String>?) : RecyclerView.Adapter<MyRecyclerViewAdapter.RecyclerHolder>() {
        
        var mList = mutableList
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder = RecyclerHolder(layoutInflater.inflate(R.layout.item_recyclerview, parent, false))
        
        override fun getItemCount(): Int = mList!!.size
        
        override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
            holder.txt.text = mList?.get(position)
            holder.txt.setOnClickListener {
                Toast.makeText(activity, "第" + position + "个条目被点击了！", Toast.LENGTH_SHORT).show()
            }
        }
        
        
        private inner class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var txt: TextView = itemView.findViewById(R.id.item_txt) as TextView
        }
        
        
    }
    
}