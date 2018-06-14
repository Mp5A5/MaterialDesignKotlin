package www.mp5a5.com.materialdesignkotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @describe
 * @author ：king9999 on 2018/6/14 19：38
 * @email：wwb199055@enn.cn
 */
class WeaponFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weapon,container,false)
    }
}