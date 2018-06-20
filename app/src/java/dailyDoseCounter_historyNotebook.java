package com.gkmonk_v3.crazybeam.yotutor;

/**
 * Created by crazybeam on 9/12/2017.
 */


        import android.app.NotificationManager;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v4.content.ContextCompat;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.GridLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.WindowManager;


        import com.gkmonk_v3.crazybeam.yotutor.fragment_spectrum.fragment_spectrum;
        import com.gkmonk_v3.crazybeam.yotutor.slidingTabs.SlidingTabLayout;
        import com.google.android.gms.ads.AdRequest;
        import com.google.android.gms.ads.AdView;
        import com.google.gson.Gson;
        import com.google.gson.reflect.TypeToken;

        import java.lang.reflect.Type;
        import java.util.List;
        import java.util.Locale;




public class dailyDoseCounter_historyNotebook extends AppCompatActivity implements ActionBar.TabListener{

    dailyDoseCounter_historyNotebook.SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;

    static final String LOG_TAG = "SlidingTabsBasicFragment";

    private SlidingTabLayout mSlidingTabLayout;



    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public  int card_rowlist_count;
    public String subject_code;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private GridLayoutManager lLayout;


    String clicked_cardView_index;

    String serverResponse;

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //actionBar.show();

        //actionBar.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.actionbar_drawable_his));
        //actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
       // actionBar.setCustomView(R.layout.layout_spectrum_actionbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.dailydosecounter_historynotebook);


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

        mSectionsPagerAdapter = new dailyDoseCounter_historyNotebook.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

       /* we are hiding sliding tab layout as right now we have only one entry

        */
        // mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        //mSlidingTabLayout.setViewPager(mViewPager);


        //=========================================================



        SharedPreferences.Editor editor;
        Gson gson;


        List<String> al_topicids;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        gson = new Gson();
        String json_get1 = prefs.getString("set_spectr_topics_id", "");
        Type type1 = new TypeToken<List<String>>(){}.getType();
        al_topicids= gson.fromJson(json_get1, type1);

        //*************************************************



        //if we come to this page from some other age then this value of "serverresponse" would be
        //null

        serverResponse = getIntent().getStringExtra("card_view_count");
      //  Log.w("Rohit", "server value =" + serverResponse);


        serverResponse = String.valueOf(al_topicids.size());//this is if we come from respective topic pages to this or some other page.

        if(prefs.getString("isbackpressed","").equals("true"))
        {


            clicked_cardView_index = getIntent().getStringExtra("clicked_card_view");

            editor.putString("clicked_card_view", clicked_cardView_index.trim());

            editor.commit();

      //      Log.w("Rohit","card clicked Indes = "+clicked_cardView_index);
        }

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


   //     Log.w("Rohit","Restart invoked");


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
    public  void onBackPressed()
    {

        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(dailyDoseCounter_historyNotebook.this);
        SharedPreferences.Editor editor1 = prefs1.edit();

        editor1.remove("clicked_card_view");

        editor1.commit();

        // super.onBackPressed();
        Intent intent = new Intent(this,Launch_page.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

        //super.onBackPressed(); //system default behaviour as we have override it in "MainClass_NOtes_Display"
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    // new class

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {

            String name = "Rohit";


            Fragment fragment = null;
            switch (position){
                case 0:

                    return fragment_spectrum.init(serverResponse);

                //fragment = new GeneralFragment();

                // break;



            }
            return fragment;
        }

        @Override
        public int getCount()
        {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Ref. Book - Spectrum/Wiki".toUpperCase(l);

            }
            return null;
        }
    }

}
