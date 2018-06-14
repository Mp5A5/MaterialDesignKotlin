package www.mp5a5.com.materialdesignkotlin

import android.app.AlertDialog
import android.os.Bundle
import android.support.design.internal.NavigationMenuView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*


fun showSnackbar(viewGroup: ViewGroup, str: String, duration: Int = 1000) {
    val snackbar = Snackbar.make(viewGroup, str, duration)
    snackbar.view.setBackgroundColor(ContextCompat.getColor(viewGroup.context, R.color.colorPrimary))
    snackbar.show()
}

class MainActivity : AppCompatActivity() {

    private var currentFragment: Fragment? = null

    private var weaponFragment: WeaponFragment? = null
    private var peopleFragment: TestFragment? = null
    private var subFragment: TestFragment? = null
    private var thingsFragment: TestFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        setContentView(R.layout.activity_main)
        initView()
        initListener()
        if (weaponFragment == null) {
            weaponFragment = WeaponFragment()
        }
        switchFragment(weaponFragment!!)
    }


    private fun initView() {
        Glide.with(this)
                .load(Constant.HEAD_IMAGE_URL)
                .into(mMainNavigationViewNv.getHeaderView(0).findViewById(R.id.iv_head))

        //去掉NavigationView滚动条
        val navigationMenuView = mMainNavigationViewNv!!.getChildAt(0) as NavigationMenuView
        navigationMenuView!!.setVerticalScrollBarEnabled(false);
    }


    private fun initListener() {
        //侧滑菜单事件
        mMainNavigationViewNv!!.setCheckedItem(R.id.nav_weapon_inc)
        mMainNavigationViewNv!!.setNavigationItemSelectedListener { item ->
            mMainNavigationViewNv!!.setCheckedItem(item.itemId)
            mMainDrawerLayoutDl!!.closeDrawers()
            when (item.itemId) {

                R.id.nav_weapon_inc -> {
                    if (weaponFragment == null) {
                        weaponFragment = WeaponFragment()
                    }
                    switchFragment(weaponFragment!!)
                    mMainBottomNavigationViewBv.selectedItemId = R.id.bottom_weapon_inc
                }

                R.id.nav_people_inc -> {
                    if (peopleFragment == null) {
                        peopleFragment = TestFragment.newInstance(getString(R.string.people_inc))
                    }
                    switchFragment(peopleFragment!!)
                    mMainBottomNavigationViewBv.selectedItemId = R.id.bottom_people_inc
                }

                R.id.nav_sub_inc -> {
                    if (subFragment == null) {
                        subFragment = TestFragment.newInstance(getString(R.string.sub_inc))
                    }
                    switchFragment(subFragment!!)
                    mMainBottomNavigationViewBv.selectedItemId = R.id.bottom_sub_inc
                }

                R.id.nav_things_inc -> {
                    if (thingsFragment == null) {
                        thingsFragment = TestFragment.newInstance(getString(R.string.thing_inc))
                    }
                    switchFragment(thingsFragment!!)
                    mMainBottomNavigationViewBv.selectedItemId = R.id.bottom_things_inc
                }

                R.id.nav_bottom_dialog -> {

                }

                R.id.nav_snack_bar -> {
                    // 弹出 SnackBar 和 Toast
                    Snackbar.make(mMainContainerFl, "弹出 SnackBar", Snackbar.LENGTH_SHORT).setAction("Cancel") {
                        Toast.makeText(this@MainActivity, "Cancel this action", Toast.LENGTH_SHORT).show()
                    }.show()

                    //showSnackbar(mMainContainerFl, "弹出 SnackBar")
                }

                R.id.nav_alert_dialog -> {
                    // 弹出 AlertDialog
                    AlertDialog.Builder(this).setTitle("title").setMessage("message").setNegativeButton("确认", null)
                            .setPositiveButton("取消", null).show()
                }
            }
            false
        }

        //底部点击事件
        mMainBottomNavigationViewBv!!.setOnNavigationItemReselectedListener {
            mMainDrawerLayoutDl!!.closeDrawers()
            when (it.itemId) {
                R.id.bottom_weapon_inc -> {
                    if (weaponFragment == null) {
                        weaponFragment = WeaponFragment()
                    }
                    switchFragment(weaponFragment!!)
                    mMainNavigationViewNv.setCheckedItem(R.id.nav_weapon_inc)
                }
                R.id.bottom_people_inc -> {
                    if (peopleFragment == null) {
                        peopleFragment = TestFragment.newInstance(getString(R.string.people_inc))
                    }
                    switchFragment(peopleFragment!!)
                    mMainNavigationViewNv.setCheckedItem(R.id.nav_people_inc)
                }
                R.id.bottom_sub_inc -> {
                    if (subFragment == null) {
                        subFragment = TestFragment.newInstance(getString(R.string.sub_inc))
                    }
                    switchFragment(subFragment!!)
                    mMainNavigationViewNv.setCheckedItem(R.id.nav_sub_inc)
                }
                R.id.bottom_things_inc -> {
                    if (thingsFragment == null) {
                        thingsFragment = TestFragment.newInstance(getString(R.string.thing_inc))
                    }
                    switchFragment(thingsFragment!!)
                    mMainNavigationViewNv.setCheckedItem(R.id.nav_things_inc)
                }
            }
            true
        }
    }

    private fun switchFragment(targetFragment: Fragment) {

        // 如果当前的fragment 就是要替换的fragment 就直接return
        if (currentFragment == targetFragment) return

        // 获得 Fragment 事务
        val transaction = supportFragmentManager.beginTransaction()
        // 如果当前Fragment不为空，则隐藏当前的Fragment
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }
        // 如果要显示的Fragment 已经添加了，那么直接 show
        if (targetFragment.isAdded) {
            transaction.show(targetFragment)
        } else {
            // 如果要显示的Fragment没有添加，就添加进去
            transaction.add(R.id.mMainContainerFl, targetFragment, targetFragment.javaClass.name)
        }
        // 事务进行提交
        transaction.commit()
        //并将要显示的Fragment 设为当前的 Fragment
        currentFragment = targetFragment
    }
}
