package com.tennissetapp;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener implements OnScrollListener {

    private int visibleThreshold = 10;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = true;

    public EndlessScrollListener() {
    }
    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
//    	C.logi("LOADING++++++++++:::::::::: " + totalItemCount + ", " + previousTotal + ", " + visibleItemCount);
    	if (totalItemCount > previousTotal) {
//            C.logi("onScroll - RETURNING - end of list");
            return;
        }
    	
        if (loading) {
//        	Log.d(C.LogTag, "LOADING0 " + totalItemCount + ", " + previousTotal);
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }
        }
//        Log.i(C.LogTag, "SCROLL-- : " + (totalItemCount - visibleItemCount) + " <= " +  (firstVisibleItem + visibleThreshold));
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // I load the next page of gigs using a background task,
            // but you can call any function here.
        	
        	//CALL TO SERVER
//        	Log.i(getClass().getSimpleName(), "LOADING PAGE: " + previousTotal + ", " + visibleThreshold + ", " + currentPage);
        	loadPage(previousTotal,visibleThreshold);
            loading = true;
        }
    }
    
    public abstract void loadPage(int startIndex, int maxResults);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
}
