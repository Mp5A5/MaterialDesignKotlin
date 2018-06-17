package www.mp5a5.com.materialdesignkotlin.view.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import www.mp5a5.com.materialdesignkotlin.R
import www.mp5a5.com.materialdesignkotlin.net.GirlEntity

/**
 * @describe
 * @author ：king9999 on 2018/6/15 10：13
 * @email：wwb199055@enn.cn
 */
class WeaponAdapter(layoutId: Int = R.layout.item_weapon) : BaseQuickAdapter<GirlEntity, BaseViewHolder>(layoutId), OnItemMoving {


    override fun convert(helper: BaseViewHolder?, item: GirlEntity?) {
        Glide.with(mContext)
                .load(item?.thumb)
                .into(helper!!.getView(R.id.iv_girl_img))
        helper.setText(R.id.tv_girl_name, item!!.title)
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