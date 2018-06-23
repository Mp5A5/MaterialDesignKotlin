package www.mp5a5.com.materialdesignkotlin.view.frag

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.transition.Explode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_weapon.*
import www.mp5a5.com.materialdesignkotlin.Constant
import www.mp5a5.com.materialdesignkotlin.R
import www.mp5a5.com.materialdesignkotlin.net.BeautifulGirlService
import www.mp5a5.com.materialdesignkotlin.net.GirlEntity
import www.mp5a5.com.materialdesignkotlin.net.GirlEntityResult
import www.mp5a5.com.materialdesignkotlin.view.act.BeautifulGirlDetailActivity
import www.mp5a5.com.materialdesignkotlin.view.adapter.WeaponAdapter
import java.util.*

/**
 * @describe
 * @author ：king9999 on 2018/6/14 19：38
 * @email：wwb199055@enn.cn
 */
class GirlFragment : Fragment(), BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {
    
    private var mAdapter: WeaponAdapter? = null
    private val startIndex = 0
    private var endIndex = 1
    private val startType = 39
    private val endType = 40
    private val isRefresh = true
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weapon, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
        initListener()
        mGirlRefreshLayoutRl.isRefreshing = true
        initData(startType, startIndex, isRefresh)
    }
    
    private fun initView() {
        mGirlRefreshLayoutRl.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN)
        mGirlRecyclerView.setHasFixedSize(true)
        mGirlRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        (mGirlRecyclerView.layoutManager as StaggeredGridLayoutManager).gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallBack())
        itemTouchHelper.attachToRecyclerView(mGirlRecyclerView)
        
    }
    
    
    private fun initAdapter() {
        mAdapter = WeaponAdapter()
        mGirlRecyclerView.adapter = mAdapter
        mAdapter!!.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT)
    }
    
    private fun initData(startType: Int, startIndex: Int, refresh: Boolean) {
        
        BeautifulGirlService.getBeautifulGirlData(startType, startIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ weaponEntityResult ->
                    mGirlRefreshLayoutRl.isRefreshing = false
                    setData(weaponEntityResult.showapi_res_code, weaponEntityResult, refresh)
                }, {
                    mGirlRefreshLayoutRl.isRefreshing = false
                    Toast.makeText(activity, "获取数据失败！", Toast.LENGTH_SHORT).show()
                })
    }
    
    private fun initListener() {
        mAdapter!!.onItemClickListener = this
        mGirlRefreshLayoutRl.setOnRefreshListener {
            mGirlRefreshLayoutRl.isRefreshing = true
            initData(startType, startIndex, isRefresh)
        }
        
        mAdapter!!.setOnLoadMoreListener(this, mGirlRecyclerView!!)
        
        mGirlFloatingActionButtonFab.setOnClickListener {
            mGirlRecyclerView.smoothScrollToPosition(0)
        }
    }
    
    private fun setData(code: Int, weaponEntityResult: GirlEntityResult, isRefresh: Boolean) {
        
        Log.e("-->", weaponEntityResult.toString())
        
        when (code) {
            0 -> {
                val iterator = weaponEntityResult.showapi_res_body
                        .entrySet().iterator()
                val weaponEntityList: MutableList<GirlEntity> = ArrayList()
                while (iterator.hasNext()) {
                    val next = iterator.next()
                    try {
                        val huaBan = Gson().fromJson(next.value, GirlEntity::class.java)
                        weaponEntityList.add(huaBan)
                    } catch (e: Exception) {
                    }
                }
                if (isRefresh) {
                    weaponEntityList.shuffle()
                    mAdapter!!.setNewData(weaponEntityList)
                } else {
                    mAdapter!!.addData(weaponEntityList)
                    mAdapter!!.loadMoreComplete()
                }
                
            }
        }
        
        
    }
    
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (adapter is WeaponAdapter) {
            activity!!.window.exitTransition = Explode()
            val weaponBean = adapter.data[position]
            val intent = Intent(activity, BeautifulGirlDetailActivity::class.java)
            intent.putExtra(Constant.GIRL_BEAN, weaponBean)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
        }
    }
    
    internal inner class ItemTouchHelperCallBack : ItemTouchHelper.Callback() {
        
        override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(dragFlags, swipeFlags)
        }
        
        override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
            mAdapter!!.onItemMoving(viewHolder!!.adapterPosition, target!!.adapterPosition)
            return false
        }
        
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
            mAdapter!!.onItemDismissing(viewHolder!!.adapterPosition)
        }
        
    }
    
    override fun onLoadMoreRequested() {
        initData(endType, endIndex++, false)
    }
}