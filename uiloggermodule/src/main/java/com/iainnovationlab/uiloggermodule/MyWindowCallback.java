package com.iainnovationlab.uiloggermodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;


public class MyWindowCallback implements Window.Callback{


    private static final String TAG = "MyWindowCallback";
    private final LoggerCallback callback;
    Activity activity;
    boolean lastFocus = true;
    int lastAction = 0;
    String name = null;
    Window.Callback localCallback;

    public MyWindowCallback(Window.Callback localCallback, Activity activity, LoggerCallback callback) {
        this.localCallback = localCallback;
        this.activity = activity;
        this.callback = callback;


    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return localCallback.dispatchKeyEvent(event);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return localCallback.dispatchKeyShortcutEvent(event);
    }

    public int getLastAction() {
        return lastAction;
    }

    public String getName() {
        return name;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if(lastAction == event.getAction())
            return localCallback.dispatchTouchEvent(event);;
        callback.log("dispatchTouchEvent");
        View control = null;
        Fragment f = activity.getFragmentManager().getPrimaryNavigationFragment();
        if(f!=null && f.isVisible())
            control = findViewAt((ViewGroup)f.getView(),event.getX(),event.getY());
        else
            control = findViewAt((ViewGroup)activity.getWindow().getDecorView(),event.getX(),event.getY());
        String controlName = "";
        try{
            if(control!=null)
                if(control.getId()!=-1)
                controlName = activity.getResources().getResourceName(control.getId());
            else if(control.getTag()!=null)
                    controlName = control.getTag().toString();
            else
                    controlName = control.getAccessibilityClassName().toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG,"could not get resource name");
        }
        Log.d(TAG,"User Clicked: "+(control==null?"no control found":controlName)+"  action preformed " + event.getAction());//view.getResources().getString(view.getId()));
        name = (control==null?"no control found":controlName);
        lastAction = event.getAction();
        return localCallback.dispatchTouchEvent(event);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent event) {
        return localCallback.dispatchTrackballEvent(event);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        return localCallback.dispatchGenericMotionEvent(event);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return localCallback.dispatchPopulateAccessibilityEvent(event);
    }

    @Override
    public View onCreatePanelView(int featureId) {
        return localCallback.onCreatePanelView(featureId);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return localCallback.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        boolean ret = localCallback.onPreparePanel(featureId, view, menu);
        return ret;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return localCallback.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return localCallback.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {
        localCallback.onWindowAttributesChanged(attrs);
    }

    @Override
    public void onContentChanged() {
        localCallback.onContentChanged();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.d("","Focus changed:"+hasFocus);
        localCallback.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onAttachedToWindow() {
        localCallback.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        localCallback.onDetachedFromWindow();
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        localCallback.onPanelClosed(featureId, menu);
    }

    @Override
    public boolean onSearchRequested() {
        return localCallback.onSearchRequested();
    }

    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return false;
    }

    @SuppressLint("NewApi")
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return localCallback.onWindowStartingActionMode(callback);
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
        return null;
    }

    @SuppressLint("NewApi")
    @Override
    public void onActionModeStarted(ActionMode mode) {
        localCallback.onActionModeStarted(mode);

    }

    @SuppressLint("NewApi")
    @Override
    public void onActionModeFinished(ActionMode mode) {
        localCallback.onActionModeFinished(mode);

    }
    private View findViewAt(ViewGroup viewGroup, float x, float y) {
        for(int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                View foundView = findViewAt((ViewGroup) child, x, y);
                if (foundView != null && foundView.isShown()) {
                    return foundView;
                }
            } else {
                int[] location = new int[2];
                child.getLocationOnScreen(location);
                Rect rect = new Rect(location[0], location[1], location[0] + child.getWidth(), location[1] + child.getHeight());
                if (rect.contains((int)x, (int)y)) {
                    return child;
                }
            }
        }

        return null;
    }

    }