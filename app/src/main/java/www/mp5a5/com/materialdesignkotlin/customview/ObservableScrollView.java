package www.mp5a5.com.materialdesignkotlin.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author ：king9999 on 2018/6/19 15：08
 * @describe
 * @email：wwb199055@enn.cn
 */
public class ObservableScrollView extends ScrollView {

  private ScrollViewListener scrollViewListener = null;

  public ObservableScrollView(Context context) {
    super(context);
  }

  public ObservableScrollView(Context context, AttributeSet attrs,
                              int defStyle) {
    super(context, attrs, defStyle);
  }

  public ObservableScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void setScrollViewListener(ScrollViewListener scrollViewListener) {
    this.scrollViewListener = scrollViewListener;
  }

  @Override
  protected void onScrollChanged(int x, int y, int oldx, int oldy) {
    super.onScrollChanged(x, y, oldx, oldy);
    if (scrollViewListener != null) {
      scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
    }
  }

  public interface ScrollViewListener {

    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);

  }
}
