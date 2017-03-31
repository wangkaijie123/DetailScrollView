package com.levylin.detailscrollview.views;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import com.levylin.detailscrollview.views.helper.ListViewHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by LinXin on 2017/3/23.
 */
public class DetailListView extends ListView implements IDetailListView {

    private Method reportScrollStateChangeMethod;
    private Method startMethod;
    private Object mFlingRunnable;
    private ListViewHelper mHelper;

    public DetailListView(Context context) {
        super(context);
        init();
    }

    public DetailListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DetailListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        setVerticalScrollBarEnabled(false);
        setOverScrollMode(OVER_SCROLL_NEVER);
        if (VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setFriction(ViewConfiguration.getScrollFriction());
            try {
                Field field = AbsListView.class.getDeclaredField("mFlingRunnable");
                field.setAccessible(true);
                mFlingRunnable = field.get(this);
                Class clazz = Class.forName("android.widget.AbsListView.FlingRunnable");
                startMethod = clazz.getDeclaredMethod("start", Integer.TYPE);
                startMethod.setAccessible(true);
                reportScrollStateChangeMethod = AbsListView.class.getDeclaredMethod("reportScrollStateChange", Integer.TYPE);
                reportScrollStateChangeMethod.setAccessible(true);
            } catch (Throwable v0) {
                v0.printStackTrace();
                mFlingRunnable = null;
                startMethod = null;
                reportScrollStateChangeMethod = null;
            }
        }
    }

    public boolean startFling(int vy) {
        if (VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fling(vy);
            return true;
        } else if (reportScrollStateChangeMethod != null && startMethod != null) {
            try {
                reportScrollStateChangeMethod.invoke(this, OnScrollListener.SCROLL_STATE_FLING);
                startMethod.invoke(mFlingRunnable, vy);
                return true;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return false;
    }

    public void setScrollView(DetailScrollView scrollView) {
        mHelper = new ListViewHelper(scrollView, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !(mHelper != null && !mHelper.onTouchEvent(ev)) && super.onTouchEvent(ev);
    }

    @Override
    public void customScrollBy(int dy) {
        smoothScrollBy(dy, 0);
    }

    @Override
    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }
}