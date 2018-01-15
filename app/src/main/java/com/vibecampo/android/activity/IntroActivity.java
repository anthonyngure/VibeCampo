package com.vibecampo.android.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.vibecampo.android.R;
import com.vibecampo.android.apptourlibrary.AppTourActivity;
import com.vibecampo.android.apptourlibrary.MaterialSlide;

/**
 * @author Vlonjat Gashi (vlonjatg)
 */
public class IntroActivity extends AppTourActivity {

    @Override
    public void init(Bundle savedInstanceState) {

        //Create pre-isCreated fragments

        Fragment firstSlide = MaterialSlide.newInstance(R.drawable.slide1,
                "Discover", "Browse communities of the things you love and care about to find your talents, skills and interests and make new friends.");

        Fragment secondSlide = MaterialSlide.newInstance(R.drawable.slide2,
                "Explore", "Engage, complete goals, show case yourself, ask questions,get tips, opportunities and trends, and get rewarded while at it.");

        Fragment thirdSlide = MaterialSlide.newInstance(R.drawable.slide3,
                "Grow", "When you grow together as a community, not even the sky can be a limit to your achievements.");

        //Add slides
        addSlide(firstSlide);
        addSlide(secondSlide);
        addSlide(thirdSlide);

    }
}
