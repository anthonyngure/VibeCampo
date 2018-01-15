package com.vibecampo.android.goals.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.vibecampo.android.R;
import com.vibecampo.android.activity.BaseActivity;
import com.vibecampo.android.goals.fragment.CreateGoalFragment;
import com.vibecampo.android.model.Community;

public class CreateGoalActivity extends BaseActivity {

    private static final String EXTRA_COMMUNITY = "extra_community";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);

        Community community = getIntent().getExtras().getParcelable(EXTRA_COMMUNITY);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentsContainer, CreateGoalFragment.newInstance(community))
                .commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void start(Context context, Community community) {
        Intent starter = new Intent(context, CreateGoalActivity.class);
        starter.putExtra(EXTRA_COMMUNITY, community);
        context.startActivity(starter);
    }

}
