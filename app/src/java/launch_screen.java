package com.gkmonk_v3.crazybeam.yotutor;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.gkmonk_v3.crazybeam.yotutor.subscribtion_page.Subscribe_User;

import java.util.Timer;
import java.util.TimerTask;

public class launch_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);


        // for future use and reset to zero

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("pushmsgcounter","0");
      // editor.putString("notificationCounter","0");  //no need  to define here otherwise create bug

        editor.commit();

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // int id = (int) System.currentTimeMillis();

        /*
        if we want push notification in separate trail use unique id for each message in notify i.e
        use 'id' variable in notify instead of '1'
         */

        manager.cancel(1);


        //==================================
         ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

       // requestWindowFeature(Window.FEATURE_NO_TITLE);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //========================




        setContentView(R.layout.activity_launch_screen);




// remove title



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance

            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }



// Auto - Redirect to other activity

        TimerTask task = new TimerTask()
        {

            @Override
            public void run()
            {
                Intent intent = new Intent(launch_screen.this, Subscribe_User.class);

              //  intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finishscreen();
            }
        };
        Timer t = new Timer();
        t.schedule(task, 5000);



    }

    private void finishscreen() {
        this.finish();


    }

    }

