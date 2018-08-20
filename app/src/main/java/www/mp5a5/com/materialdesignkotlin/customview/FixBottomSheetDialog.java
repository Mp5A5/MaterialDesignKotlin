package www.mp5a5.com.materialdesignkotlin.customview;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author ：mp5a5 on 2018/8/14 20：51
 * @describe
 * @email：wwb199055@126.com
 */
public class FixBottomSheetDialog extends BottomSheetDialog {

  public FixBottomSheetDialog(@NonNull Context context) {
    super(context);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStatusBarBackground();
    setBehaviorCallback();
  }

  /**
   * 解决6.0的机器上当对话框弹出时系统状态栏变黑的bug
   */
  private void setStatusBarBackground() {
    int screenHeight = getScreenHeight();
    int statusBarHeight = getStatusBarHeight();
    int dialogHeight = screenHeight - statusBarHeight;
    getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
        dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
  }


  /**
   * 解决dialog打开→关闭后，不能再打开的bug
   */
  private void setBehaviorCallback() {
    View view = getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
    final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(view);
    bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override
      public void onStateChanged(@NonNull View bottomSheet, int newState) {
        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
          dismiss();
          bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
      }

      @Override
      public void onSlide(@NonNull View bottomSheet, float slideOffset) {
      }
    });
  }


  /**
   * 屏幕高度
   */
  private int getScreenHeight() {
    return getContext().getResources().getDisplayMetrics().heightPixels;
  }

  /**
   * 状态栏高度
   */
  private int getStatusBarHeight() {
    int statusBarHeight = 0;
    Resources resources = getContext().getResources();
    int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0)
      statusBarHeight = resources.getDimensionPixelSize(resourceId);
    return statusBarHeight;
  }

}
