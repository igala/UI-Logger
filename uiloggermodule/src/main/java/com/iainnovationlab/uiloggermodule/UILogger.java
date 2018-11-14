package com.iainnovationlab.uiloggermodule;

import android.app.Activity;
import android.util.Log;
import android.view.Window;

public class UILogger {
    private static MyWindowCallback myCallback = null;

    public UILogger(Activity activity,LoggerCallback log)
    {

        final Window win = activity.getWindow();
        final Window.Callback localCallback = win.getCallback();
         myCallback = new MyWindowCallback(localCallback, activity,log);
        win.setCallback(myCallback);
    }
}
