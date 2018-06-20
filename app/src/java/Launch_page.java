package com.gkmonk_v3.crazybeam.yotutor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;


import android.support.v7.app.ActionBar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gkmonk_v3.crazybeam.yotutor.BackgroundWorkerThreads.MCQs_CardView_Data;
import com.gkmonk_v3.crazybeam.yotutor.BackgroundWorkerThreads.StandardBook_CardView_Data;
import com.gkmonk_v3.crazybeam.yotutor.BackgroundWorkerThreads.updates_notification_worker;
import com.gkmonk_v3.crazybeam.yotutor.India_Through_Map.Tabs_India_through_Map;
import com.gkmonk_v3.crazybeam.yotutor.Launch_Page_Layout.adapter.intro_page_adapter;
import com.gkmonk_v3.crazybeam.yotutor.Launch_Page_Layout.tab_view.tab_view;



import com.gkmonk_v3.crazybeam.yotutor.NCERT_class_10th.show_subject_list_processor_10th;
import com.gkmonk_v3.crazybeam.yotutor.NCERT_class_11th.show_subject_list_processor_11th;
import com.gkmonk_v3.crazybeam.yotutor.NCERT_class_12th.show_subject_list_processor_12th;
import com.gkmonk_v3.crazybeam.yotutor.NCERT_class_6th.show_subject_list_processor;
import com.gkmonk_v3.crazybeam.yotutor.NCERT_class_7th.show_subject_list_processor_7th;
import com.gkmonk_v3.crazybeam.yotutor.NCERT_class_8th.show_subject_list_processor_8th;
import com.gkmonk_v3.crazybeam.yotutor.NCERT_class_9th.show_subject_list_processor_9th;
import com.gkmonk_v3.crazybeam.yotutor.Terms_and_Condition.terms_and_condition;
//import com.gkmonk_v3.crazybeam.yotutor.Volley.Volley_Post_Request;
import com.gkmonk_v3.crazybeam.yotutor.subscribtion_page.Subscribe_User;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;


public class Launch_page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    InterstitialAd mInterstitialAd;
    public Toolbar toolbar;
    public DrawerLayout drawer;
    public  FloatingActionButton fab;
    public String subject_code;
    FrameLayout progressBarHolder;
   boolean isbookmarked;

    TextView notificationCounter;

    SharedPreferences prefs1 ;
    SharedPreferences.Editor editor1 ;
    ViewPager viewPager;

    View background;


    String[] arr_desc;
    String[] arr_activity;


    ArrayList<String> al_desc = new ArrayList<String>();
    ArrayList<String> al_activity = new ArrayList<String>();


    TextView terms;

    GradientDrawable gradientdrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_launch_page);




      ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Gkmonk");
       // actionbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ab));
        actionbar.setSubtitle("Shrine Of High Quality Notes..");


        //*****************Actionbar with color effects code *****************

        //ActionBar actionbar = getActionBar();

        gradientdrawable = new GradientDrawable();

        gradientdrawable.setColors(new int[]{
                Color.parseColor("#e8fbfc"),
                Color.parseColor("#01ebfa")
        });

        gradientdrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        gradientdrawable.setShape(GradientDrawable.RECTANGLE);

        actionbar.setBackgroundDrawable(gradientdrawable);



        //******************************Code Ends ******************



    //   actionBar.hide();

    //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //       WindowManager.LayoutParams.FLAG_FULLSCREEN);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window w = getWindow(); // in Activity's onCreate() for instance

            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        }


        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);




        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//++++++++++++++++++Launch Page - Sliding Tab Layout+++++++++++++++++++++++++++++++

//********************************************

        //////////////////
        SharedPreferences prefs2 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor2 = prefs2.edit();


        String emailid = prefs2.getString("emailid","");


  String desc = prefs2.getString("desc","");
        String activity = prefs2.getString("activity","");
        String updates_count = prefs2.getString("updates_count","");



    //    Log.w("Rohit", " Updates Response_2 " + desc + " |"+activity+" | "+updates_count);



try {

    arr_desc = desc.split(",");
    arr_activity = activity.split(",");

   // for (int i = 0; i < arr_desc.length; i++) {

    for (int i = arr_desc.length-1; i >=0; i--) {  // this will show latest updates first.

        al_desc.add(arr_desc[i].replaceAll("^\"|\"$", "").trim());

        al_activity.add(arr_activity[i].replaceAll("^\"|\"$", "").trim());

      //  Log.w("Rohit", " Updates Response_3 " + al_desc.get(i) + " |" + al_activity.get(i));


    } // for loop ends

}

catch (Exception e)
{
    //Log.w("Rohit","Launch Page exception e = "+e);
}


//******************************************



        background = findViewById(R.id.background_view);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        intro_page_adapter ipa = new intro_page_adapter(getSupportFragmentManager(),this,updates_count,al_activity,al_desc,drawer,progressBarHolder,emailid);
        viewPager.setAdapter(ipa);
        viewPager.setCurrentItem(1); // to set default viewpager



        //my_custom_toolbar mcustomToolbar = (my_custom_toolbar) findViewById(R.id.my_toolbar);
        //mcustomToolbar.setupwithviewpager(viewPager);

        tab_view mtab_view = (tab_view) findViewById(R.id.tabs);
        mtab_view.setupwithviewpager(viewPager);


        //******push notification counter realted code***********************//



         prefs1 = PreferenceManager.getDefaultSharedPreferences(this);
         editor1 = prefs1.edit();


       notificationCounter  = (TextView) findViewById(R.id.notification_counter);



        String str = prefs1.getString("notificationCounter", "");

      //  Log.w("Rohit","2_Ncntr "+str);

        if(str.trim().equals("0") || str.trim().equals("")  )
        {
            notificationCounter.setVisibility(View.INVISIBLE);
        }

      else
        {
            notificationCounter.setVisibility(View.VISIBLE);
            notificationCounter.setText(str);
        }



//***************************************************************




        final int light_purple= ContextCompat.getColor(this, R.color.light_purple);
        final int amber = ContextCompat.getColor(this,R.color.amber);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                              @Override
                                              public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
                                              {
                                                  if(position==0)
                                                  {


                                                      background.setBackgroundColor(amber);
                                                      background.setAlpha(1-positionOffset); // this code is to change color on swipe.
                                                  }
                                                  else if(position==1)
                                                  {

                                                      background.setBackgroundColor(light_purple);
                                                      background.setAlpha(1-positionOffset);
                                                  }

                                                  else if(position==2)
                                                  {

                                                      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Launch_page.this);
                                                      SharedPreferences.Editor editor = prefs.edit();


                                                      editor.putString("notificationCounter", "0"); //set default value.
                                                      editor.commit();  // save changes


                                                      editor1.putString("pushmsgcounter", "0");

                                                      editor1.commit();


                                                      notificationCounter.setVisibility(View.INVISIBLE);

                                                      NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                                                      // int id = (int) System.currentTimeMillis();

                                                      //
                                                      //  if we want push notification in separate trail use unique id for each message in notify i.e
                                                      //  use 'id' variable in notify instead of '1'
                                                      //

                                                      manager.cancel(1);

                                                      background.setBackgroundColor(amber);
                                                      background.setAlpha(1-positionOffset);

                                                  }
                                                  else
                                                  {
                                                      background.setBackgroundColor(amber);
                                                      background.setAlpha(1-positionOffset);
                                                  }


                                              }

                                              @Override
                                              public void onPageSelected(int position)
                                              {

                                              }

                                              @Override
                                              public void onPageScrollStateChanged(int state)
                                              {



                                              }
                                          }

        );


//++++++++++++++++++Launch Page Sliding Tab Layout+++++++++++++++++++++++++++++++


        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("my-event"));


    }  //onCreateMethod Ends





    public void onFloatingButtonClick(View view)
   {
//open drawere on floating action button click.

      drawer.openDrawer(Gravity.LEFT);

   }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();

            Intent intent = new Intent(this,Subscribe_User.class);
            startActivity(intent);



        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.launch_page, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    //    int id = item.getItemId();

        //noinspection SimplifiableIfStatement
     //   if (id == R.id.action_settings) {
     //       return true;
      //  }

        return super.onOptionsItemSelected(item);
    }


//MCQs Start.

    public void onClick(View view)
    {

        if (view.getId() == R.id.nav_polity)
        {

            String isbackpressed="false";
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Launch_page.this);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("isbackpressed",isbackpressed);
            editor.commit();


           String mcqsflag = "mcqs_polity";

            MCQs_CardView_Data mcqs_cardView_data = new MCQs_CardView_Data(Launch_page.this,progressBarHolder,isbookmarked);
            mcqs_cardView_data.execute(mcqsflag);

            //*******************Code - Interstial Ads***********************************

            mInterstitialAd = new InterstitialAd(Launch_page.this);

            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



            get_Ads_Config gac = new get_Ads_Config();

            AdRequest adRequest = gac.getAdsMode();

            // Load ads into Interstitial Ads
            mInterstitialAd.loadAd(adRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });



//**************************Code - Interstial Ads******************


        }
        else if (view.getId() == R.id.nav_geography)
        {

            String isbackpressed="false";
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Launch_page.this);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("isbackpressed",isbackpressed);
            editor.commit();


            String mcqsflag = "mcqs_geo";

            MCQs_CardView_Data mcqs_cardView_data = new MCQs_CardView_Data(Launch_page.this,progressBarHolder,isbookmarked);
            mcqs_cardView_data.execute(mcqsflag);
            //*******************Code - Interstial Ads***********************************

            mInterstitialAd = new InterstitialAd(Launch_page.this);

            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



            get_Ads_Config gac = new get_Ads_Config();

            AdRequest adRequest = gac.getAdsMode();

            // Load ads into Interstitial Ads
            mInterstitialAd.loadAd(adRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });



//**************************Code - Interstial Ads******************


        }

        else if (view.getId() == R.id.nav_history)
        {
            String isbackpressed="false";
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Launch_page.this);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("isbackpressed",isbackpressed);
            editor.commit();


            String mcqsflag = "mcqs_his";

            MCQs_CardView_Data mcqs_cardView_data = new MCQs_CardView_Data(Launch_page.this,progressBarHolder,isbookmarked);
            mcqs_cardView_data.execute(mcqsflag);
            //*******************Code - Interstial Ads***********************************

            mInterstitialAd = new InterstitialAd(Launch_page.this);

            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



            get_Ads_Config gac = new get_Ads_Config();

            AdRequest adRequest = gac.getAdsMode();

            // Load ads into Interstitial Ads
            mInterstitialAd.loadAd(adRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });



//**************************Code - Interstial Ads******************

        }
        else if (view.getId() == R.id.nav_currentAffairs)
        {
            String isbackpressed="false";
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Launch_page.this);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("isbackpressed",isbackpressed);
            editor.commit();


            String mcqsflag = "mcqs_ca";

            MCQs_CardView_Data mcqs_cardView_data = new MCQs_CardView_Data(Launch_page.this,progressBarHolder,isbookmarked);
            mcqs_cardView_data.execute(mcqsflag);

            //*******************Code - Interstial Ads***********************************

            mInterstitialAd = new InterstitialAd(Launch_page.this);

            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



            get_Ads_Config gac = new get_Ads_Config();

            AdRequest adRequest = gac.getAdsMode();

            // Load ads into Interstitial Ads
            mInterstitialAd.loadAd(adRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });



//**************************Code - Interstial Ads******************

        }

////***********************************************************************************************//
// *                                                                                               *//
// *                              STARTS - Standard Book Sections                                  *//                               *//
// *                                                                                               *//
// *                                                                                               *//
////***********************************************************************************************//

        else if (view.getId()== R.id.notes_polity)
        {

            String isbackpressed="false";
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Launch_page.this);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("isbackpressed",isbackpressed);
            editor.commit();

// Create custom dialog object
            final Dialog dialog = new Dialog(this,R.style.NoTitleDialog);// style we have added to remove default dialog title.
            // Include dialog.xml file
            dialog.setContentView(R.layout.standard_books_custom_dialog);
            // Set dialog title
           // dialog.setTitle("Custom Dialog");


            // set values for custom dialog components - text, image and button

            TextView Subject = (TextView) dialog.findViewById(R.id.subject);
            Subject.setText("Polity");

            ImageView imgreadAll = (ImageView) dialog.findViewById(R.id.img_readAll);
            TextView readAll = (TextView) dialog.findViewById(R.id.readAll_text);

            ImageView imgreviseAll = (ImageView) dialog.findViewById(R.id.img_reviseAll);
            TextView reviseAll = (TextView) dialog.findViewById(R.id.reviseAll_text);

            dialog.show();



            readAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    // Close dialog
                    dialog.dismiss();
                    isbookmarked=false;  // means without bookmarked
                    String Subject_Flag = "sb_polity";

                    StandardBook_CardView_Data standardBook_cardView_data = new StandardBook_CardView_Data(Launch_page.this,progressBarHolder,isbookmarked);
                    standardBook_cardView_data.execute(Subject_Flag);

                    //*******************Code - Interstial Ads***********************************

                    mInterstitialAd = new InterstitialAd(Launch_page.this);

                    // set the ad unit ID
                    mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



                    get_Ads_Config gac = new get_Ads_Config();

                    AdRequest adRequest = gac.getAdsMode();

                    // Load ads into Interstitial Ads
                    mInterstitialAd.loadAd(adRequest);

                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            }
                        }
                    });



//**************************Code - Interstial Ads******************
                }
            });

            reviseAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    // Close dialog
                    dialog.dismiss();

                    isbookmarked=true; // means with bookmarked
                    String Subject_Flag = "sb_polity";
                    StandardBook_CardView_Data standardBook_cardView_data = new StandardBook_CardView_Data(Launch_page.this,progressBarHolder,isbookmarked);
                    standardBook_cardView_data.execute(Subject_Flag);
                    //*******************Code - Interstial Ads***********************************

                    mInterstitialAd = new InterstitialAd(Launch_page.this);

                    // set the ad unit ID
                    mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



                    get_Ads_Config gac = new get_Ads_Config();

                    AdRequest adRequest = gac.getAdsMode();

                    // Load ads into Interstitial Ads
                    mInterstitialAd.loadAd(adRequest);

                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            }
                        }
                    });



//**************************Code - Interstial Ads******************
                }
            });


        }



//*********      GEOGRAPHY - SECTION STARTS


        else if (view.getId()== R.id.notes_geography)
        {

            String isbackpressed="false";
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Launch_page.this);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("isbackpressed",isbackpressed);
            editor.commit();


// Create custom dialog object
            final Dialog dialog = new Dialog(this,R.style.NoTitleDialog);// style we have added to remove default dialog title.
            // Include dialog.xml file
            dialog.setContentView(R.layout.standard_books_custom_dialog);
            // Set dialog title
            // dialog.setTitle("Custom Dialog");


            // set values for custom dialog components - text, image and button

            TextView Subject = (TextView) dialog.findViewById(R.id.subject);
            Subject.setText("Geography");

            ImageView imgreadAll = (ImageView) dialog.findViewById(R.id.img_readAll);
            TextView readAll = (TextView) dialog.findViewById(R.id.readAll_text);

            ImageView imgreviseAll = (ImageView) dialog.findViewById(R.id.img_reviseAll);
            TextView reviseAll = (TextView) dialog.findViewById(R.id.reviseAll_text);

            dialog.show();



            readAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    // Close dialog
                    dialog.dismiss();
                    isbookmarked=false;  // means without bookmarked
                    String Subject_Flag = "sb_geography";

                    StandardBook_CardView_Data standardBook_cardView_data = new StandardBook_CardView_Data(Launch_page.this,progressBarHolder,isbookmarked);
                    standardBook_cardView_data.execute(Subject_Flag);
                    //*******************Code - Interstial Ads***********************************

                    mInterstitialAd = new InterstitialAd(Launch_page.this);

                    // set the ad unit ID
                    mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



                    get_Ads_Config gac = new get_Ads_Config();

                    AdRequest adRequest = gac.getAdsMode();

                    // Load ads into Interstitial Ads
                    mInterstitialAd.loadAd(adRequest);

                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            }
                        }
                    });



//**************************Code - Interstial Ads******************
                }
            });

            reviseAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    // Close dialog
                    dialog.dismiss();

                    isbookmarked=true; // means with bookmarked
                    String Subject_Flag = "sb_geography";
                    StandardBook_CardView_Data standardBook_cardView_data = new StandardBook_CardView_Data(Launch_page.this,progressBarHolder,isbookmarked);
                    standardBook_cardView_data.execute(Subject_Flag);
                    //*******************Code - Interstial Ads***********************************

                    mInterstitialAd = new InterstitialAd(Launch_page.this);

                    // set the ad unit ID
                    mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



                    get_Ads_Config gac = new get_Ads_Config();

                    AdRequest adRequest = gac.getAdsMode();

                    // Load ads into Interstitial Ads
                    mInterstitialAd.loadAd(adRequest);

                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            }
                        }
                    });



//**************************Code - Interstial Ads******************
                }
            });


        }


//*******************  HISTORY SECTION - STARTS

        else if (view.getId() == R.id.notes_history)
        {
            String isbackpressed="false";
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Launch_page.this);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("isbackpressed",isbackpressed);
            editor.commit();



            // Create custom dialog object
            final Dialog dialog = new Dialog(this,R.style.NoTitleDialog);// style we have added to remove default dialog title.
            // Include dialog.xml file
            dialog.setContentView(R.layout.standard_books_custom_dialog);
            // Set dialog title
            // dialog.setTitle("Custom Dialog");


            // set values for custom dialog components - text, image and button

            TextView Subject = (TextView) dialog.findViewById(R.id.subject);
            Subject.setText("History");

            ImageView imgreadAll = (ImageView) dialog.findViewById(R.id.img_readAll);
            TextView readAll = (TextView) dialog.findViewById(R.id.readAll_text);

            ImageView imgreviseAll = (ImageView) dialog.findViewById(R.id.img_reviseAll);
            TextView reviseAll = (TextView) dialog.findViewById(R.id.reviseAll_text);

            dialog.show();



            readAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    // Close dialog
                    dialog.dismiss();
                    isbookmarked=false;  // means without bookmarked
                    String Subject_Flag = "sb_history";

                    StandardBook_CardView_Data standardBook_cardView_data = new StandardBook_CardView_Data(Launch_page.this,progressBarHolder,isbookmarked);
                    standardBook_cardView_data.execute(Subject_Flag);
                    //*******************Code - Interstial Ads***********************************

                    mInterstitialAd = new InterstitialAd(Launch_page.this);

                    // set the ad unit ID
                    mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



                    get_Ads_Config gac = new get_Ads_Config();

                    AdRequest adRequest = gac.getAdsMode();

                    // Load ads into Interstitial Ads
                    mInterstitialAd.loadAd(adRequest);

                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            }
                        }
                    });



//**************************Code - Interstial Ads******************
                }
            });

            reviseAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    // Close dialog
                    dialog.dismiss();

                    isbookmarked=true; // means with bookmarked
                    String Subject_Flag = "sb_history";
                    StandardBook_CardView_Data standardBook_cardView_data = new StandardBook_CardView_Data(Launch_page.this,progressBarHolder,isbookmarked);
                    standardBook_cardView_data.execute(Subject_Flag);
                    //*******************Code - Interstial Ads***********************************

                    mInterstitialAd = new InterstitialAd(Launch_page.this);

                    // set the ad unit ID
                    mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



                    get_Ads_Config gac = new get_Ads_Config();

                    AdRequest adRequest = gac.getAdsMode();

                    // Load ads into Interstitial Ads
                    mInterstitialAd.loadAd(adRequest);

                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            }
                        }
                    });



//**************************Code - Interstial Ads******************
                }
            });


        }



////***********************************************************************************************//
// *                                                                                               *//
// *                              END - Standard Book Sections                                  *//                               *//
// *                                                                                               *//
// *                                                                                               *//
////***********************************************************************************************//



////***********************************************************************************************//
// *                                                                                               *//
// *                              Starts - NCERTs Book Sections                                  *//                               *//
// *                                                                                               *//
// *                                                                                               *//
////***********************************************************************************************//

        else if(view.getId() ==R.id.notes_ncert6)
        {

            Intent intent = new Intent(Launch_page.this,show_subject_list_processor.class);

            // intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
          //  intent.putExtra("Subject_Code","ca01");

            startActivity(intent);
            //*******************Code - Interstial Ads***********************************

            mInterstitialAd = new InterstitialAd(Launch_page.this);

            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



            get_Ads_Config gac = new get_Ads_Config();

            AdRequest adRequest = gac.getAdsMode();

            // Load ads into Interstitial Ads
            mInterstitialAd.loadAd(adRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });



//**************************Code - Interstial Ads******************
        }

        else if(view.getId() ==R.id.notes_ncert7)
        {


            Intent intent = new Intent(Launch_page.this,show_subject_list_processor_7th.class);

            // intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra("Subject_Code","ca01");

            startActivity(intent);
            //*******************Code - Interstial Ads***********************************

            mInterstitialAd = new InterstitialAd(Launch_page.this);

            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



            get_Ads_Config gac = new get_Ads_Config();

            AdRequest adRequest = gac.getAdsMode();

            // Load ads into Interstitial Ads
            mInterstitialAd.loadAd(adRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });



//**************************Code - Interstial Ads******************
        }

        else if(view.getId()==R.id.notes_ncert8)
        {

            Intent intent = new Intent(Launch_page.this,show_subject_list_processor_8th.class);

            // intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra("Subject_Code","ca01");

            startActivity(intent);
            //*******************Code - Interstial Ads***********************************

            mInterstitialAd = new InterstitialAd(Launch_page.this);

            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



            get_Ads_Config gac = new get_Ads_Config();

            AdRequest adRequest = gac.getAdsMode();

            // Load ads into Interstitial Ads
            mInterstitialAd.loadAd(adRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });



//**************************Code - Interstial Ads******************
        }

        else if(view.getId() ==R.id.notes_ncert9)
        {

            Intent intent = new Intent(Launch_page.this,show_subject_list_processor_9th.class);

            // intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra("Subject_Code","ca01");

            startActivity(intent);
            //*******************Code - Interstial Ads***********************************

            mInterstitialAd = new InterstitialAd(Launch_page.this);

            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



            get_Ads_Config gac = new get_Ads_Config();

            AdRequest adRequest = gac.getAdsMode();

            // Load ads into Interstitial Ads
            mInterstitialAd.loadAd(adRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });



//**************************Code - Interstial Ads******************
        }

        else if(view.getId() ==R.id.notes_ncert10)
        {



            Intent intent = new Intent(Launch_page.this,show_subject_list_processor_10th.class);

            // intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra("Subject_Code","ca01");

            startActivity(intent);
            //*******************Code - Interstial Ads***********************************

            mInterstitialAd = new InterstitialAd(Launch_page.this);

            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



            get_Ads_Config gac = new get_Ads_Config();

            AdRequest adRequest = gac.getAdsMode();

            // Load ads into Interstitial Ads
            mInterstitialAd.loadAd(adRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });



//**************************Code - Interstial Ads******************
        }

        else if(view.getId()==R.id.notes_ncert11)
        {


            Intent intent = new Intent(Launch_page.this,show_subject_list_processor_11th.class);

            // intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra("Subject_Code","ca01");

            startActivity(intent);
            //*******************Code - Interstial Ads***********************************

            mInterstitialAd = new InterstitialAd(Launch_page.this);

            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



            get_Ads_Config gac = new get_Ads_Config();

            AdRequest adRequest = gac.getAdsMode();

            // Load ads into Interstitial Ads
            mInterstitialAd.loadAd(adRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });



//**************************Code - Interstial Ads******************
        }

        else if(view.getId()==R.id.notes_ncert12)
        {

            Intent intent = new Intent(Launch_page.this,show_subject_list_processor_12th.class);

            // intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra("Subject_Code","ca01");

            startActivity(intent);



//*******************Code - Interstial Ads***********************************

            mInterstitialAd = new InterstitialAd(Launch_page.this);

            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



            get_Ads_Config gac = new get_Ads_Config();

            AdRequest adRequest = gac.getAdsMode();

            // Load ads into Interstitial Ads
            mInterstitialAd.loadAd(adRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });



//**************************Code - Interstial Ads******************

        }


        else if(view.getId()==R.id.india_maps)
        {

            String isbackpressed="false";
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Launch_page.this);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("isbackpressed",isbackpressed);
            editor.commit();


            String mcqsflag = "maps";

            MCQs_CardView_Data mcqs_cardView_data = new MCQs_CardView_Data(Launch_page.this,progressBarHolder,isbookmarked);
            mcqs_cardView_data.execute(mcqsflag);

            //*******************Code - Interstial Ads***********************************

            mInterstitialAd = new InterstitialAd(Launch_page.this);

            // set the ad unit ID
            mInterstitialAd.setAdUnitId(getString(R.string.topics_interstitial_ad));



            get_Ads_Config gac = new get_Ads_Config();

            AdRequest adRequest = gac.getAdsMode();

            // Load ads into Interstitial Ads
            mInterstitialAd.loadAd(adRequest);

            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });



//**************************Code - Interstial Ads******************
        }


////***********************************************************************************************//
// *                                                                                               *//
// *                              Ends - NCERTs Book Sections                                  *//                               *//
// *                                                                                               *//
// *                                                                                               *//
////***********************************************************************************************//


        else if (view.getId() == R.id.nav_terms)
        {





            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(Launch_page.this);

// Setting Dialog Title
            alertDialog2.setTitle("About");

// Setting Dialog Message
            alertDialog2.setMessage(Html.fromHtml("<p>Version 1.0</p> <p> Copyright  <font color ='Blue'><u>gkmonk 2018.</u></font></p><p>All Rights reserved.</p><p>mail us : <font color ='Blue'><u>support@gkmonk.com</u></font></p>"));



// Setting Icon to Dialog
            alertDialog2.setIcon(R.mipmap.companylogo);


// Setting Positive "Yes" Btn
            alertDialog2.setPositiveButton("Terms",
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {

                            //*********************************************




                                // Write your code here to execute after dialog
                                Intent intent = new Intent(Launch_page.this, terms_and_condition.class);


                                startActivity(intent);


                        }
                    });


// Setting Negative "NO" Btn
            alertDialog2.setNegativeButton("OK",
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // Write your code here to execute after dialog


                            dialog.dismiss();


                        }
                    });

            // Showing Alert Dialog
            alertDialog2.show();

            //******************************************************/



        }  // else if ends here.

     /*   else if (view.getId() == R.id.nav_disclaimer) {

        }
*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        return true;
    }




    // handler for received Intents for the "my-event" event
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
         //   Log.w("Rohit", "Got message: " + message);

            //*******Code to show notification counter in HOme page

            String str = prefs1.getString("notificationCounter", "");

        //    Log.w("Rohit","3_Ncntr "+Integer.parseInt(str));

            if(str.trim().equals("0"))
            {
                notificationCounter.setVisibility(View.INVISIBLE);
            }

            else
            {
                notificationCounter.setVisibility(View.VISIBLE);
                notificationCounter.setText(str);
            }


        }
    };

  /*  @Override
    protected void onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }
*/




    @Override
    public void onRestart()
    {

        // it will call only once when we come to app from homescreen or by clicking app.



        super.onRestart();
        // put your code here...


     //   Log.w("Rohit","Restart invoked");


        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor1 = prefs1.edit();


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // int id = (int) System.currentTimeMillis();

        //
        //  if we want push notification in separate trail use unique id for each message in notify i.e
        //  use 'id' variable in notify instead of '1'
        //

        manager.cancel(1);

        // for future use and reset to zero


        editor1.putString("pushmsgcounter", "0");

        editor1.commit();


//*******Code to show notification counter in HOme page

        String str = prefs1.getString("notificationCounter", "");

      //  Log.w("Rohit","2_Ncntr "+Integer.parseInt(str));

        if(str.trim().equals("0"))
        {
            notificationCounter.setVisibility(View.INVISIBLE);
        }

        else
        {
            notificationCounter.setVisibility(View.VISIBLE);
            notificationCounter.setText(str);
        }


        Intent intent = new Intent(this,Subscribe_User.class);
        startActivity(intent);

    }  //onRestart ends here.



}
