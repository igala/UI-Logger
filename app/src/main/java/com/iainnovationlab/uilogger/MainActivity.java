package com.iainnovationlab.uilogger;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.iainnovationlab.uiloggermodule.LoggerCallback;
import com.iainnovationlab.uiloggermodule.UILogger;

public class MainActivity extends AppCompatActivity
 implements PlusOneFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new UILogger(this, new LoggerCallback() {
            @Override
            public void log(String event) {
                Log.d("MainActivity",event);
            }
        });
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.test,new PlusOneFragment()).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
