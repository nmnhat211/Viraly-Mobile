package com.example.viralyapplication.ui.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.viralyapplication.R;
import com.example.viralyapplication.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainToolbarActivity extends BaseFragmentActivity {

    private static MainToolbarActivity sInstance = null;
    protected Context mContext;
    private TextView tvMainTitle;
    private ImageView ivActionBack, ivActionSearch, ivActionMenuBar, ivLogoApp, ivActionHome;
    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;

//    @Override
//    public void setContentView(int layoutResID) {
//        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_main_home_layout, null);
//        FrameLayout activityContent = fullView.findViewById(R.id.fl_fragment_main_home);
//        getLayoutInflater().inflate(layoutResID, activityContent, true);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
//                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        super.setContentView(fullView);

//        initView();
//    }

    public MainToolbarActivity() {
    }

    private MainToolbarActivity(Context context) {
        sInstance = (MainToolbarActivity) context.getApplicationContext();
    }

    public static MainToolbarActivity getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MainToolbarActivity(context);
        }
        return sInstance;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_layout);
        initView();
        mContext = this;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        tvMainTitle = findViewById(R.id.main_title_toolbar);
        ivActionBack = findViewById(R.id.iv_back_tool_bar);
        ivActionSearch = findViewById(R.id.iv_search_tool_bar);
        ivActionMenuBar = findViewById(R.id.iv_menu_bar);
        ivActionHome = findViewById(R.id.iv_home_tool_bar);
        ivLogoApp = findViewById(R.id.iv_logo_app);

        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mViewPager = findViewById(R.id.vp_adapter_main_home);

        ivActionBack.setOnClickListener(this);
        ivActionSearch.setOnClickListener(this);
        ivActionMenuBar.setOnClickListener(this);

        setDisplayTitle(true);
        setTitleBar(getString(R.string.home));
        setViewPager();
        setViewBottomNavigation();
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_back_tool_bar:
                finish();
                break;
            case R.id.iv_menu_bar:
                Toast.makeText(mContext, "menu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_search_tool_bar:
                Toast.makeText(mContext, "Search", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    protected void setDisplayTitle(boolean value) {
        tvMainTitle.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    protected void setTitleBar(String title) {
        if (TextUtils.isEmpty(title)) {
            setDisplayTitle(false);
        } else {
            setDisplayTitle(true);
            tvMainTitle.setText(title);
        }
    }

    protected void setDisplayLogo(boolean value){
        ivLogoApp.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    protected void setDisplayHome(boolean value){
        ivActionHome.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    protected void setDisplayMenu(boolean value) {
        ivActionMenuBar.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    protected void setDisplayBack(boolean value) {
        ivActionBack.setVisibility(value ? View.VISIBLE : View.GONE);
    }


    protected void setDisplaySearch(boolean value) {
        ivActionBack.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    protected void setFragment(Fragment fragment) {
        super.setContentView(R.layout.activity_toolbar_layout);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_fragment, fragment)
                .addToBackStack(null)
                .commit();
        initView();
    }

    protected void setFragment(android.app.Fragment fragment) {
        super.setContentView(R.layout.activity_toolbar_layout);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment, fragment);
        transaction.commit();
        initView();
    }

    protected void setFragment(Fragment fragment, String tag) {
        super.setContentView(R.layout.activity_toolbar_layout);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_fragment, fragment, tag)
                .addToBackStack(null)
                .commit();
        initView();
    }

    /**
     * Add more fragment
     *
     * @param fragment
     */
    protected void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    protected void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    protected void removeFragment(String tag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment oldFragment = fm.findFragmentByTag(tag);
        fm.beginTransaction().remove(oldFragment).commitAllowingStateLoss();
        fm.popBackStack();
    }



    private void setViewBottomNavigation(){
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_newsfeed:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.nav_profile:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.nav_notify:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.nav_menu_search:
                        mViewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

    }

    private void setViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.nav_newsfeed).setCheckable(true);
                        setDisplayTitle(true);
                        setDisplayHome(true);
                        setTitleBar(getString(R.string.home));
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.nav_profile).setCheckable(true);
                        setDisplayTitle(true);
                        setDisplayHome(true);
                        setTitleBar(getString(R.string.my_profile));
                        break;
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.nav_notify).setCheckable(true);
                        setDisplayTitle(true);
                        setDisplayHome(true);
                        setTitleBar(getString(R.string.notification_text));
                        break;
                    case 3:
                        mBottomNavigationView.getMenu().findItem(R.id.nav_menu_search).setCheckable(true);
                        setDisplayTitle(true);
                        setDisplayHome(true);
                        setTitleBar(getString(R.string.search_text1));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

