package www.mp5a5.com.materialdesignkotlin

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_weapon.*
import java.util.*

/**
 * @describe
 * @author ：king9999 on 2018/6/14 19：38
 * @email：wwb199055@enn.cn
 */
class WeaponFragment : Fragment(), BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    private var mAdapter: WeaponAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weapon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
        initData()
        initListener()
        mWeaponRefreshLayoutRl.isRefreshing = true
        getData(0)
    }

    private fun initView() {
        mWeaponRefreshLayoutRl.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN)
        mWeaponRecyclerView.setHasFixedSize(true)
        mWeaponRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        (mWeaponRecyclerView.layoutManager as StaggeredGridLayoutManager).gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallBack())
        itemTouchHelper.attachToRecyclerView(mWeaponRecyclerView)

    }


    private fun initAdapter() {
        mAdapter = WeaponAdapter()
        mWeaponRecyclerView.adapter = mAdapter
        mAdapter!!.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT)
    }

    private fun initData() {


    }

    private fun initListener() {
        mAdapter!!.onItemClickListener = this
        mWeaponRefreshLayoutRl.setOnRefreshListener {
            mWeaponRefreshLayoutRl.isRefreshing = true
            getData(1)
        }

        /*mAdapter!!.setOnItemClickListener(object : BaseQuickAdapter.OnItemClickListener {

            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                if (adapter is WeaponAdapter) {
                    *//*activity!!.window.exitTransition = Explode()
                    val weaponBean = adapter.data[position]
                    val intent = Intent(activity, WeaponDetailActivity::class.java)
                    intent.putExtra(Constants.EXTRA_WEAPON_BEAN, weaponBean)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())*//*
                }
            }


        })*/
        mAdapter!!.setOnLoadMoreListener(this, mWeaponRecyclerView!!)

        mWeaponFloatingActionButtonFab.setOnClickListener {
            mWeaponRecyclerView.smoothScrollToPosition(0)
        }
    }

    private fun getData(i: Int) {
        MyClient.create(MyService::class.java)
                .veaponInfo
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<WeaponResult> {
                    override fun accept(t: WeaponResult?) {
                        setData(t)
                    }
                }, object : Consumer<Throwable> {
                    override fun accept(t: Throwable?) {
                        mWeaponRefreshLayoutRl.isRefreshing = false
                        Toast.makeText(activity, "获取数据失败！", Toast.LENGTH_SHORT).show()
                    }

                })


    }

    private fun setData(t: WeaponResult?) {
        when (t!!.status) {
            0 -> {
                mAdapter!!.setNewData(t.data)
                mWeaponRefreshLayoutRl.isRefreshing = false
            }
            1 -> {
                Toast.makeText(activity, "刷新成功！", Toast.LENGTH_SHORT).show()
                Collections.shuffle(t.data)
                mAdapter!!.setNewData(t.data)
                mWeaponRefreshLayoutRl.isRefreshing = false
            }
            2 -> {
                Thread(Runnable {
                    Thread.sleep(800)
                    activity!!.runOnUiThread {
                        mAdapter!!.addData(t.data)
                        mAdapter!!.loadMoreComplete()
                    }
                }).start()
            }
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (adapter is WeaponAdapter) {
            /*activity!!.window.exitTransition = Explode()
            val weaponBean = adapter.data[position]
            val intent = Intent(activity, WeaponDetailActivity::class.java)
            intent.putExtra(Constants.EXTRA_WEAPON_BEAN, weaponBean)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())*/
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
        getData(2)
    }
}