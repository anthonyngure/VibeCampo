package com.vibecampo.android.apptourlibrary;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vibecampo.android.R;
import com.vibecampo.android.activity.FragmentActivity;
import com.vibecampo.android.fragment.SignInFragment;
import com.vibecampo.android.fragment.SignUpFragment;

import java.util.List;
import java.util.Vector;

/**
 * @author Vlonjat Gashi (vlonjatg)
 */
public abstract class AppTourActivity extends AppCompatActivity {

    private final List<Fragment> fragments = new Vector<>();
    protected Button singInButton;
    protected Button signUpButton;
    protected int currentPosition;
    private ViewPager introViewPager;
    private RelativeLayout controlsRelativeLayout;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private PagerAdapter pagerAdapter;
    private int activeDotColor;
    private int inactiveDocsColor;
    private int numberOfSlides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (Build.VERSION.SDK_INT >= 19) {
            //Set status bar to semi-transparent
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_app_tour);

        introViewPager = findViewById(R.id.introViewPager);
        controlsRelativeLayout = findViewById(R.id.controlsRelativeLayout);
        singInButton =  findViewById(R.id.signInBtn);
        signUpButton = findViewById(R.id.signUpBtn);
        dotsLayout =  findViewById(R.id.viewPagerCountDots);

        activeDotColor = Color.RED;
        inactiveDocsColor = Color.WHITE;

        //Instantiate the PagerAdapter.
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        introViewPager.setAdapter(pagerAdapter);

        init(savedInstanceState);

        numberOfSlides = fragments.size();


        //Instantiate the indicator dots if there are more than one slide
        setViewPagerDots();

        setListeners();
    }

    public abstract void init(@Nullable Bundle savedInstanceState);

    /**
     * Add a slide to the intro
     *
     * @param fragment Fragment of the slide to be added
     */
    public void addSlide(@NonNull Fragment fragment) {
        fragments.add(fragment);
        pagerAdapter.notifyDataSetChanged();
    }

    public void setCurrentSlide(int position) {
        introViewPager.setCurrentItem(position, true);
    }

    private void setListeners() {
        introViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                currentPosition = position;

                //Hide NEXT button if last slide item and set DONE button
                //visible, otherwise hide Done button and set NEXT button visible
                if (position == numberOfSlides - 1) {

                }

                //Set dots
                if (numberOfSlides > 1) {
                    //Set current inactive dots color
                    for (int i = 0; i < numberOfSlides; i++) {
                        dots[i].setTextColor(inactiveDocsColor);
                    }

                    //Set current active dot color
                    dots[position].setTextColor(activeDotColor);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        singInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                FragmentActivity.start(AppTourActivity.this, SignInFragment.newInstance(),
                        getString(R.string.sign_in), null);

            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {

                FragmentActivity.start(AppTourActivity.this, SignUpFragment.newInstance(),
                        getString(R.string.sign_up), null);

            }
        });
    }

    private void setViewPagerDots() {
        dots = new TextView[numberOfSlides];

        //Set first inactive dots color
        for (int i = 0; i < numberOfSlides; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml(getString(R.string.dot)));
            dots[i].setTextSize(30);
            dots[i].setTextColor(inactiveDocsColor);
            dotsLayout.addView(dots[i]);
        }

        //Set first active dot color
        dots[0].setTextColor(activeDotColor);
    }

    @Override
    public void onBackPressed() {
        if (currentPosition == 0) {
            super.onBackPressed();
        } else {
            setCurrentSlide((currentPosition - 1));
        }
    }
}
