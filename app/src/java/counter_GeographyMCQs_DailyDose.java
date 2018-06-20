package com.gkmonk_v3.crazybeam.yotutor;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.gkmonk_v3.crazybeam.yotutor.BackgroundWorkerThreads.StandardBook_CardView_Data;


import com.gkmonk_v3.crazybeam.yotutor.Fragment_Geography.Climatology;
import com.gkmonk_v3.crazybeam.yotutor.Fragment_Geography.Geomorphology;
import com.gkmonk_v3.crazybeam.yotutor.Fragment_Geography.Indian_Geography;
import com.gkmonk_v3.crazybeam.yotutor.Fragment_Geography.Oceanography;
import com.gkmonk_v3.crazybeam.yotutor.Fragment_History.AncientHistory_Fragment;
import com.gkmonk_v3.crazybeam.yotutor.Fragment_History.Art_and_culture;
import com.gkmonk_v3.crazybeam.yotutor.Fragment_History.MedievalHistory_Fragment;
import com.gkmonk_v3.crazybeam.yotutor.Fragment_History.ModernHistory_Fragment;
import com.gkmonk_v3.crazybeam.yotutor.Fragment_Polity.Polity_TopicsMCQs.MainClass_Notes_Display;
import com.gkmonk_v3.crazybeam.yotutor.slidingTabs.SlidingTabLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;


public class counter_GeographyMCQs_DailyDose extends AppCompatActivity implements ActionBar.TabListener{

    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;

    static final String LOG_TAG = "SlidingTabsBasicFragment";

    private SlidingTabLayout mSlidingTabLayout;



    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public  int card_rowlist_count;

    public String mcqs_topics_flag;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private GridLayoutManager lLayout;

    String indexFlag;


    String index_0_mcqs_count;
    String index_1_mcqs_count;
    String index_2_mcqs_count;
    String index_3_mcqs_count;



    FrameLayout progressBarHolder;

    String clicked_cardView_index;

    AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.counter_geographymcqs_dailydose);



//**********************Load Ad ***********************

        mAdView = (AdView) findViewById(R.id.adView);


        get_Ads_Config gac = new get_Ads_Config();

        AdRequest adRequest = gac.getAdsMode();


        mAdView.loadAd(adRequest);


       /* mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(getApplicationContext(), "Ad is loaded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Ad is closed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getApplicationContext(), "Ad failed to load! error code: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLeftApplication() {
                Toast.makeText(getApplicationContext(), "Ad left application!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(getApplicationContext(), "Ad is opened!", Toast.LENGTH_SHORT).show();
            }
        });

        */

        //***************************************

        /*==============================================================
        code for sliding tabs can be seen in two classes
                first class - SlidingTabLayout
                And,

                        Second Class - SlidingTabStrip
        =========================================================*/

        //Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        // setSupportActionBar(topToolBar);
        // topToolBar.setLogo(R.drawable.logo);
        // topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);

        mViewPager.setAdapter(mSectionsPagerAdapter);

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);



        //*************************************************************************************************

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(counter_GeographyMCQs_DailyDose.this);
        SharedPreferences.Editor editor = prefs.edit();




        index_0_mcqs_count = getIntent().getStringExtra("index_0");

        index_1_mcqs_count = getIntent().getStringExtra("index_1");

        index_2_mcqs_count = getIntent().getStringExtra("index_2");

        index_3_mcqs_count = getIntent().getStringExtra("index_3");




        if(prefs.getString("isbackpressed","").equals("true"))
        {
            indexFlag = getIntent().getStringExtra("indexFlag_his");

           // Log.w("Rohit","indexFlag on back "+indexFlag);

            mViewPager.setCurrentItem(Integer.parseInt(indexFlag.trim()));

            clicked_cardView_index = getIntent().getStringExtra("clicked_card_view");

            editor.putString("clicked_card_view", clicked_cardView_index.trim());

            editor.commit();

          //  Log.w("Rohit","card clicked Indes = "+clicked_cardView_index);
        }



        if(prefs.getString("index_0_mcqs_count", "").equals(""))
        {

            // Always call when we come from Launch page to this page.

            editor.putString("index_0_mcqs_count", index_0_mcqs_count.trim());

            editor.putString("index_1_mcqs_count", index_1_mcqs_count.trim());

            editor.putString("index_2_mcqs_count", index_2_mcqs_count.trim());

            editor.putString("index_3_mcqs_count", index_3_mcqs_count.trim());


            editor.commit();



           // Log.w("Rohit", "UP : index 0 Flag = " + prefs.getString("index_0_mcqs_count", ""));


        }

        else {

            // // Always call when we come from Main_Notes_Display page to this page.


            index_0_mcqs_count = prefs.getString("index_0_mcqs_count", "");

            index_1_mcqs_count = prefs.getString("index_1_mcqs_count", "");

            index_2_mcqs_count = prefs.getString("index_2_mcqs_count", "");

            index_3_mcqs_count = prefs.getString("index_3_mcqs_count", "");


           // Log.w("Rohit", "Not execute at start");




        }  // else ends



//*************************************************************************************************




    }
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }


    @Override
    public void onRestart()
    {

        // it will call only once when we come to app from homescreen or by clicking app.



        super.onRestart();
        // put your code here...


      //  Log.w("Rohit","Restart invoked");


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



    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
    {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }


    @Override
    public  void onBackPressed()
    {
        // super.onBackPressed();

        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(counter_GeographyMCQs_DailyDose.this);
        SharedPreferences.Editor editor1 = prefs1.edit();

        editor1.remove("index_0_mcqs_count");

        editor1.remove("index_1_mcqs_count");

        editor1.remove("index_2_mcqs_count");

        editor1.remove("index_3_mcqs_count");



        editor1.commit();

        Intent intent = new Intent(this,Launch_page.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

        //super.onBackPressed(); //system default behaviour as we have override it in "MainClass_NOtes_Display"
    }


    // below code will create TABS.

    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(counter_GeographyMCQs_DailyDose.this);
        SharedPreferences.Editor editor = prefs.edit();

        public SectionsPagerAdapter(FragmentManager fm) {


            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {

            String name = "Test";


            Fragment fragment = null;
            switch (position){
                case 0:

                    return Geomorphology.init(index_0_mcqs_count);

                //fragment = new GeneralFragment();

                // break;

                case 1:
                    return Oceanography.init(index_1_mcqs_count);

                case 2:
                    return Climatology.init(index_2_mcqs_count);

                case 3:
                    return  Indian_Geography.init(index_3_mcqs_count);


            }


            return fragment;
        }

        @Override
        public int getCount()
        {
            return 4;
        }





        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position)
            {

                case 0:
                    return "Geomorphology".toUpperCase(l);
                case 1:
                    return "Oceanography & Climatic Regions".toUpperCase(l);
                case 2:
                    return "Climatology".toUpperCase(l);
                case 3:
                    return "Indian Geography".toUpperCase(l);


            }
            return null;
        }


    } // inner class ends








}
