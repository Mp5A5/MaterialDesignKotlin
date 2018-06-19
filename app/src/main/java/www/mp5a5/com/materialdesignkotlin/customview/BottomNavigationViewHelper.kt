package www.mp5a5.com.materialdesignkotlin.customview

import android.annotation.SuppressLint
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView

/**
 * @describe
 * @author ：king9999 on 2018/6/19 20：34
 * @email：wwb199055@enn.cn
 */
class BottomNavigationViewHelper {
    
    companion object {
        @SuppressLint("RestrictedApi")
        fun disableShiftMode(navigationView: BottomNavigationView) {
            val menuView = navigationView.getChildAt(0) as BottomNavigationMenuView
            try {
                val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
                shiftingMode.isAccessible = true
                shiftingMode.setBoolean(menuView, false)
                shiftingMode.isAccessible = false
                
                val childCount = menuView.getChildCount()
                for (i in 0 until childCount) {
                    val itemView = menuView.getChildAt(i) as BottomNavigationItemView
                    itemView.setShiftingMode(false)
                    itemView.setChecked(itemView.itemData.isChecked)
                }
            } catch (e: Exception) {
                e.printStackTrace();
            }
        }
    }
}