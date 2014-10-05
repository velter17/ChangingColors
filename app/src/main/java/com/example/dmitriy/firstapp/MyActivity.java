package com.example.dmitriy.firstapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import java.util.*;
import android.os.Handler;

public class MyActivity extends Activity {

    FrameLayout l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        l = (FrameLayout) findViewById(R.id.FrameLayout1);
        Timer t = new Timer();
        ColorTimer ct = new ColorTimer();
        t.schedule(ct, 0, 100);
    }

    class ColorTimer extends TimerTask 
	{

        public void run() 
		{

            runOnUiThread(new Runnable() 
			{

                public void run() 
				{
                    Random rand = new Random();

                    l.setBackgroundColor(Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256) ));

                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
