package www.mp5a5.com.materialdesignkotlin.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.Objects;

import www.mp5a5.com.materialdesignkotlin.R;

/**
 * @author ：mp5a5 on 2018/8/20 10：11
 * @describe
 * @email：wwb199055@126.com
 */
public class BottomSheetFragment extends BottomSheetDialogFragment {

  /**
   * 顶部向下偏移量
   */
  private int topOffset = 0;
  private BottomSheetBehavior<FrameLayout> behavior;

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    if (getContext() == null) {
      return super.onCreateDialog(savedInstanceState);
    }
    return new BottomSheetDialog(getContext(), R.style.TransparentBottomSheetStyle);
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
      savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onStart() {
    super.onStart();
    // 设置软键盘不自动弹出
    Objects.requireNonNull(getDialog().getWindow()).setSoftInputMode(WindowManager.LayoutParams
        .SOFT_INPUT_STATE_HIDDEN);
    BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) getDialog();
    FrameLayout view = bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
    if (view != null) {
      CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
      layoutParams.height = getHeight();
      behavior = BottomSheetBehavior.from(view);
      // 初始为展开状态
      behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
  }


  /**
   * 获取屏幕高度
   *
   * @return height
   */
  private int getHeight() {

    int height = 1920;
    if (getContext() != null) {
      WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
      Point point = new Point();
      if (manager != null) {
        manager.getDefaultDisplay().getSize(point);
        height = point.y - getTopOffset();
      }
    }
    return height;
  }

  public int getTopOffset() {
    return topOffset;
  }

  public void setTopOffset(int topOffset) {
    this.topOffset = topOffset;
  }

  public void onClose() {
    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
  }

  public BottomSheetBehavior<FrameLayout> getBehavior() {
    return behavior;
  }


  @Override
  public void onDestroy() {
    super.onDestroy();
    //解除缓存View和当前ViewGroup的关联

  }
}
