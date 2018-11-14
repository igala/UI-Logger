package com.iainnovationlab.uiloggermodule;

import android.app.Activity;
import android.util.Log;
import android.view.Window;

public class UILogger {
    private static MyWindowCallback myCallback = null;

    public UILogger(Activity activity)
    {

        final Window win = activity.getWindow();
        final Window.Callback localCallback = win.getCallback();
         myCallback = new MyWindowCallback(localCallback, activity, new LoggerCallback() {
            @Override
            public void log(String event) {
                Log.d("test",event);
            }
        });
        win.setCallback(myCallback);
    }
}
