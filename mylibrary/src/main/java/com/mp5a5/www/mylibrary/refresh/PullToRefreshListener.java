package com.mp5a5.www.mylibrary.refresh;

/**
 * Created by cjj on 2015/8/4.
 * 刷新回调接口
 */
public interface PullToRefreshListener {
    /**
     * 刷新中。。。
     * @param refreshLayout
     */
    void onRefresh(RefreshLayout refreshLayout);
}
