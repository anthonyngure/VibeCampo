package com.vibecampo.android.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.vibecampo.android.R;
import com.vibecampo.android.fragment.AccountFragment;
import com.vibecampo.android.fragment.BlankFragment;
import com.vibecampo.android.fragment.HomeFragment;
import com.vibecampo.android.fragment.PostToFragment;
import com.vibecampo.android.notifications.fragment.NotificationsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements OnTabSelectListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(4);
        mBottomBar.setOnTabSelectListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        PostToFragment.newInstance().show(getSupportFragmentManager(), "DIALOG");
    }

    @Override
    public void onTabSelected(int tabId) {
        if (tabId == R.id.tab_home) {
            getToolbar().setVisibility(View.GONE);
        } else {
            getToolbar().setVisibility(View.VISIBLE);
        }
        switch (tabId) {
            case R.id.tab_home:
                if (mViewPager.getCurrentItem() != 0) {
                    mViewPager.setCurrentItem(0);
                }
                break;
            case R.id.tab_notifications:
                if (mViewPager.getCurrentItem() != 1) {
                    getToolbar().setTitle(R.string.title_notifications);
                    mViewPager.setCurrentItem(1);
                }
                break;
            case R.id.tab_chats:
                if (mViewPager.getCurrentItem() != 2) {
                    getToolbar().setTitle(R.string.title_chats);
                    mViewPager.setCurrentItem(2);
                }
                break;
            case R.id.tab_account:
                if (mViewPager.getCurrentItem() != 3) {
                    getToolbar().setTitle(getUser().getFullName());
                    mViewPager.setCurrentItem(3);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            getToolbar().setVisibility(View.GONE);
        } else {
            getToolbar().setVisibility(View.VISIBLE);
        }
        switch (position) {
            case 0:
                mBottomBar.getTabWithId(R.id.tab_home).performClick();
                break;
            case 1:
                mBottomBar.getTabWithId(R.id.tab_notifications).performClick();
                break;
            case 2:
                mBottomBar.getTabWithId(R.id.tab_chats).performClick();
                break;
            case 3:
                mBottomBar.getTabWithId(R.id.tab_account).performClick();
                break;
            default:
                mBottomBar.getTabWithId(R.id.tab_home).performClick();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    static class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return HomeFragment.newInstance();
                case 1:
                    return NotificationsFragment.newInstance();
                case 2:
                    return BlankFragment.newInstance();
                case 3:
                    return AccountFragment.newInstance();
                default:
                    return BlankFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "page "+position;
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
