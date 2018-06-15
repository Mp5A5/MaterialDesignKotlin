package www.mp5a5.com.materialdesignkotlin

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @describe
 * @author ：king9999 on 2018/6/15 10：13
 * @email：wwb199055@enn.cn
 */
class WeaponAdapter(layoutId: Int = R.layout.item_weapon) : BaseQuickAdapter<WeaponEntity, BaseViewHolder>(layoutId), OnItemMoving {


    override fun convert(helper: BaseViewHolder?, item: WeaponEntity?) {
        Glide.with(mContext)
                .load(item?.imageUrl)
                .into(helper!!.getView(R.id.iv_weapon))
        helper.setText(R.id.tv_weapon_name, item!!.name)
    }

    override fun onItemMoving(startPosition: Int, endPosition: Int) {
        val start = mData.removeAt(startPosition)
        mData.add(if (endPosition > startPosition) endPosition - 1 else endPosition, start)
        notifyItemMoved(startPosition, endPosition)
    }

    override fun onItemDismissing(position: Int) {
        mData.removeAt(position)
        notifyItemRemoved(position)
    }

}

interface OnItemMoving {
    fun onItemMoving(startPosition: Int, endPosition: Int)
    fun onItemDismissing(position: Int)
}