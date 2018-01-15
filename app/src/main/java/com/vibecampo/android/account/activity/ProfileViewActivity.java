package com.vibecampo.android.account.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vibecampo.android.R;
import com.vibecampo.android.activity.BaseActivity;
import com.vibecampo.android.model.User;
import com.vibecampo.android.network.Meta;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.toshngure.basecode.images.NetworkImage;
import ke.co.toshngure.basecode.view.MaterialTextView;


public class ProfileViewActivity extends BaseActivity {

    public static final String EXTRA_USER = "extra_user";
    private static final String TAG = ProfileViewActivity.class.getSimpleName();
    protected User user;
    @BindView(R.id.avatar)
    NetworkImage mAvatar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    private boolean isMe = false;

    @BindView(R.id.phoneTV)
    TextView phoneTV;

    @BindView(R.id.emailTV)
    TextView emailTV;

    @BindView(R.id.contactsView)
    View contactsView;

    @BindView(R.id.hometownTV)
    TextView hometownTV;

    @BindView(R.id.currentCityTV)
    TextView currentCityTV;

    @BindView(R.id.residenceView)
    View residenceView;

    @BindView(R.id.genderTV)
    TextView genderTV;

    @BindView(R.id.birthdayTV)
    TextView birthdayTV;

    @BindView(R.id.friendsTV)
    TextView friendsTV;

    @BindView(R.id.genderAndBirthdayView)
    View genderAndBirthdayView;

    @BindView(R.id.bioLETV)
    MaterialTextView bioLETV;

    @BindView(R.id.bioEditIV)
    ImageView bioEditIV;

    @BindView(R.id.bioView)
    View bioView;


    @BindView(R.id.content)
    View content;

    @BindView(R.id.progressBarLoader)
    ProgressBar progressBar;

    @BindView(R.id.verifiedIV)
    ImageView verifiedIV;

    private View.OnClickListener onFriendsViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*FragmentActivity.start(
                    ProfileViewActivity.this,
                    UsersFragment.newInstance(UsersFragment.FRIENDS, user, UsersFragment.UserClickAction.VIEW_USER),
                    getString(R.string.friends),
                    user.getName()
            );*/
        }
    };

    private View.OnClickListener onVibesViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*FragmentActivity.start(
                    ProfileViewActivity.this,
                    FeedFragment.newInstance(user),
                    getString(R.string.vibes),
                    user.getName()
            );*/
        }
    };

    private View.OnClickListener showFriendsPrivacyOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Utils.showPrivacyDialog(getThis(), user.getFriendsVisibleTo());
        }
    };


    private View.OnClickListener showVibesPrivacyOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Utils.showPrivacyDialog(getThis(), user.getFeedVisibleTo());
        }
    };

    @BindView(R.id.messageView)
    View messageView;
    @BindView(R.id.updatedAtRTV)
    MaterialTextView updatedAtTV;
    @BindView(R.id.friendshipBtn)
    Button friendshipBtn;

    /*private User getUserFromBundle(Bundle bundle) {
        String userString = bundle.getString(BackEnd.Response.DATA);
        BeeLog.d(TAG, "getUserFromBundle, "+userString);
        try {
            JSONObject userObject = new JSONObject(userString);
            return UserUtils.getUserFromObject(userObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return getUser();
        }
    }*/

    public static void start(Context context, User user) {
        Intent starter = new Intent(context, ProfileViewActivity.class);
        starter.putExtra(ProfileViewActivity.EXTRA_USER, user);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String intentAction = getIntent().getAction();
        /*if ((intentAction != null) && (intentAction.equalsIgnoreCase(AppFirebaseMessagingService.ACTION_FRIEND_REQUEST_ACCEPTED)
                || intentAction.equalsIgnoreCase(AppFirebaseMessagingService.ACTION_SENT_FRIEND_REQUEST))) {
            user = getUserFromBundle(getIntent().getExtras());
        } else {
            user = getIntent().getExtras().getParcelable(EXTRA_USER);
        }*/

        user = getIntent().getExtras().getParcelable(EXTRA_USER);

        ButterKnife.bind(this);

        if (user.getUserId() == getUser().getUserId()) {
            user = getUser();
            isMe = true;
            content.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            setUpUserInfo();
            messageView.setVisibility(View.GONE);
            friendshipBtn.setVisibility(View.GONE);
        } else {
            content.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            //friendshipBtn.setUser(user, true);
            //friendshipBtn.disableInbuiltClick();
            /*friendshipBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FriendshipBottomSheetDialogFragment.newInstance(user)
                            .show(getSupportFragmentManager(), user.getUsername());
                }
            });*/
            findViewById(R.id.friendsView).setOnClickListener(null);
            messageView.setVisibility(View.GONE); //Will be shown depending on user privacy after loading
            findViewById(R.id.editProfileTV).setVisibility(View.GONE);
            findViewById(R.id.vibitsView).setVisibility(View.GONE);
            connect();
        }

        //Configure Avatar
        //mAvatar.setBorderOverlayEnabled(5, Color.WHITE);

        mAvatar.loadImageFromNetwork(user.getAvatar());

        setupUserStats();

        /*if ((intentAction != null) && intentAction.equalsIgnoreCase(AppFirebaseMessagingService.ACTION_SENT_FRIEND_REQUEST)){
            FriendshipBottomSheetDialogFragment.newInstance(user)
                    .show(getSupportFragmentManager(), user.getUsername());
        }*/

    }


    public void setupUserStats() {

        getSupportActionBar().setTitle(user.getFullName());
        getSupportActionBar().setSubtitle(String.format(getString(R.string.username_format), user.getUsername()));

        if (isMe) {
            //((TextView) findViewById(R.id.vibitsTV)).setText(String.valueOf(user.getStats().getVibits()));
        }

        ((TextView) findViewById(R.id.nameTV)).setText(String.valueOf(user.getFullName()));
        ((TextView) findViewById(R.id.usernameTV)).setText(String.format(getString(R.string.username_format), user.getUsername()));

        /*if (user.isVerified()) {
            verifiedIV.setVisibility(View.VISIBLE);
        } else {
            verifiedIV.setVisibility(View.GONE);
        }*/
    }


    private void setUpUserInfo() {

        if (!isMe) {
            /*if ((user.getCanBeMessagedBy() == BackEnd.PrivacyCodes.FRIENDS)
                    && ((user.getFriendshipActionUserId() != BackEnd.FriendshipStatus.REJECTED)
                    || (user.getFriendshipStatus() != BackEnd.FriendshipStatus.REJECTED))) {
                messageView.setVisibility(View.GONE);
            } else if ((user.getCanBeMessagedBy() == BackEnd.PrivacyCodes.PRIVATE)) {
                messageView.setVisibility(View.GONE);
            } else {
                messageView.setVisibility(View.VISIBLE);
            }*/

            /*if ((user.getFriendsVisibleTo() == BackEnd.PrivacyCodes.FRIENDS)
                    && ((user.getFriendshipActionUserId() != BackEnd.FriendshipStatus.REJECTED)
                    || (user.getFriendshipStatus() != BackEnd.FriendshipStatus.REJECTED))) {
                findViewById(R.id.friendsView).setOnClickListener(showFriendsPrivacyOnClick);
            } else if ((user.getFriendsVisibleTo() == BackEnd.PrivacyCodes.PRIVATE)) {
                findViewById(R.id.friendsView).setOnClickListener(showFriendsPrivacyOnClick);
            } else {
                findViewById(R.id.friendsView).setOnClickListener(onFriendsViewClickListener);
            }*/

            /*if ((user.getFeedVisibleTo() == BackEnd.PrivacyCodes.FRIENDS)
                    && ((user.getFriendshipActionUserId() != BackEnd.FriendshipStatus.REJECTED)
                    || (user.getFriendshipStatus() != BackEnd.FriendshipStatus.REJECTED))) {
                findViewById(R.id.vibesView).setOnClickListener(showVibesPrivacyOnClick);
            } else if ((user.getFeedVisibleTo() == BackEnd.PrivacyCodes.PRIVATE)) {
                findViewById(R.id.vibesView).setOnClickListener(showVibesPrivacyOnClick);
            } else {
                findViewById(R.id.vibesView).setOnClickListener(onVibesViewClickListener);
            }*/

        } else {

            findViewById(R.id.vibesView).setOnClickListener(onVibesViewClickListener);
            findViewById(R.id.friendsView).setOnClickListener(onFriendsViewClickListener);
        }

        //friendsTV.setText(String.valueOf(user.getStats().getFriends()));

        //((TextView) findViewById(R.id.vibesTV)).setText(String.valueOf(user.getStats().getVibes()));

        if (!isMe) {
            bioEditIV.setVisibility(View.GONE);
        }

        /*if ((user.getBio() == null) || TextUtils.isEmpty(user.getBio())) {
            if (isMe) {
                bioLETV.setText(R.string.add_bio);
                bioLETV.setTextColor(Color.RED);
            }
        } else {
            bioLETV.setText(user.getBio());
        }*/

        //boolean phoneAvailable = (user.getPhone() != null) && !TextUtils.isEmpty(user.getPhone());

        /*if (!phoneAvailable) {
            if (isMe) {
                phoneTV.setText(R.string.add_phone);
                phoneTV.setTextColor(Color.RED);
            } else {
                phoneTV.setVisibility(View.GONE);
            }
        } else {
            phoneTV.setText(user.getPhone());
        }*/

        boolean emailAvailable = (user.getEmail() != null) && !TextUtils.isEmpty(user.getEmail());

        if (!emailAvailable) {
            if (isMe) {
                emailTV.setText(R.string.add_email);
                emailTV.setTextColor(Color.RED);
            } else {
                emailTV.setVisibility(View.GONE);
            }
        } else {
            emailTV.setText(user.getEmail());
        }

        /*if (!isMe && !emailAvailable && !phoneAvailable) {
            contactsView.setVisibility(View.GONE);
        }*/

        //boolean hometownAvailable = (user.getHometown() != null) && !TextUtils.isEmpty(user.getHometown());

        /*if (!hometownAvailable) {
            if (isMe){
                hometownTV.setText(R.string.add_hometown);
                hometownTV.setTextColor(Color.RED);
            } else {
                hometownTV.setVisibility(View.GONE);
            }
        } else {
            hometownTV.setText("Lives in " + user.getHometown() + ".");
        }*/

        //boolean currentCityAvailable = (user.getCurrentCity() != null) && !TextUtils.isEmpty(user.getCurrentCity());

        /*if (!currentCityAvailable) {
            if (isMe){
                currentCityTV.setText(R.string.add_current_city);
                currentCityTV.setTextColor(Color.RED);
            } else {
                currentCityTV.setVisibility(View.GONE);
            }
        } else {
            currentCityTV.setText("From " + user.getCurrentCity() + ".");
        }*/

        /*if (!hometownAvailable && !currentCityAvailable && !isMe) {
            residenceView.setVisibility(View.GONE);
        }*/

        //genderTV.setText(Utils.resolveGender(user.getGender()));

        //boolean birthdayAvailable = (user.getBirthday() != null) && !TextUtils.isEmpty(user.getBirthday());
        /*if (!birthdayAvailable) {
            ((TextView) findViewById(R.id.genderBirthdayHeaderTV)).setText(R.string.gender);
            if (isMe){
                birthdayTV.setText(R.string.add_birthday);
                birthdayTV.setTextColor(Color.RED);
            } else {
                birthdayTV.setVisibility(View.GONE);
            }
        } else {
            birthdayTV.setText(DatesHelper.formatSqlTimestampForDisplay(user.getBirthday()));
        }*/


        /*if ((user.getInstitutions() != null) && (user.getInstitutions().size() > 0)) {
            institutionsRV.setLayoutManager(new LinearLayoutManager(this));
            InstitutionListAdapter mAdapter = new InstitutionListAdapter(this);
            institutionsRV.setAdapter(mAdapter);
            mAdapter.clear();
            mAdapter.addItems(user.getInstitutions());
        } else {
            if (isMe){
                ((TextView) findViewById(R.id.institutionsHeaderTV)).setText(R.string.add_institution);
                ((TextView) findViewById(R.id.institutionsHeaderTV)).setTextColor(Color.RED);
            } else  {
                institutionsView.setVisibility(View.GONE);
            }
        }*/

        /*if (isMe){
            updatedAtTV.setReferenceTime(DatesHelper.formatSqlTimestamp(getUser().getUpdatedAt()));
        } else {
            findViewById(R.id.updatedAtView).setVisibility(View.GONE);
        }*/

        /*friendshipBtn.disableInbuiltClick();
        friendshipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendshipBottomSheetDialogFragment.newInstance(user)
                        .show(getSupportFragmentManager(), user.getUsername());
            }
        });*/
    }


    /*private void applyPalette(Palette palette) {
        int primaryDark = ContextCompat.getColor(this, R.color.colorPrimaryDark);
        int primary = ContextCompat.getColor(this, R.color.colorPrimary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        supportStartPostponedEnterTransition();
    }*/

    //Bio editing and Others editing starts activity for result
    //The values of the user are changed
    //They have to be displayed
    //Only occurs for me
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        user = getUser();
        setUpUserInfo();
    }

    @OnClick(R.id.editProfileTV)
    public void editProfile() {
        //startActivityForResult(new Intent(this, ProfileEditActivity.class), 100); //Code doesn't matter
    }

    @OnClick(R.id.birthdayTV)
    public void addBirthday(){

    }
    @OnClick(R.id.hometownTV)
    public void addHometown(){
        //startActivityForResult(new Intent(this, ProfileEditActivity.class), 100); //Code doesn't matter
    }
    @OnClick(R.id.currentCityTV)
    public void addCurrentCity(){
        //startActivityForResult(new Intent(this, ProfileEditActivity.class), 100); //Code doesn't matter
    }

    @OnClick(R.id.vibitsView)
    public void redeemVibits() {
        //startActivity(new Intent(this, RedeemVibitsActivity.class)); //Code doesn't matter
    }

    @OnClick(R.id.messageView)
    public void message() {
        //ChatActivity.start(this, user);
    }


    @OnClick(R.id.bioLETV)
    public void updateBio() {
        /*Intent intent = new Intent(ProfileViewActivity.this, ProfileItemEditActivity.class);
        intent.putExtra(ProfileItemEditActivity.EXTRA_EDIT_ITEM, ProfileItemEditActivity.BIO);
        startActivityForResult(intent, 100); //Code doesn't matter*/
    }

    @OnClick(R.id.bioEditIV)
    public void editBio() {
        /*Intent intent = new Intent(ProfileViewActivity.this, ProfileItemEditActivity.class);
        intent.putExtra(ProfileItemEditActivity.EXTRA_EDIT_ITEM, ProfileItemEditActivity.BIO);
        startActivityForResult(intent, 100); //Code doesn't matter*/
    }

    @OnClick(R.id.phoneTV)
    public void updatePhone() {
        /*Intent intent = new Intent(this, ProfileItemEditActivity.class);
        intent.putExtra(ProfileItemEditActivity.EXTRA_EDIT_ITEM, ProfileItemEditActivity.PHONE);
        startActivity(intent);*/
    }

    @OnClick(R.id.emailTV)
    public void updateEmail() {
        /*Intent intent = new Intent(this, ProfileItemEditActivity.class);
        intent.putExtra(ProfileItemEditActivity.EXTRA_EDIT_ITEM, ProfileItemEditActivity.EMAIL);
        startActivity(intent);*/
    }


    @Override
    public void connect() {
        super.connect();
        //Client.getInstance().get(Client.absoluteUrl(BackEnd.EndPoints.USERS + "/" + user.getId()), this);
    }

    @Override
    public void onConnectionStarted() {
        super.onConnectionStarted();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConnectionFailed(int statusCode, JSONObject response) {
        super.onConnectionFailed(statusCode, response);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onSuccessResponse(JSONObject data, Meta meta) {
        super.onSuccessResponse(data, meta);
        //user = UserUtils.getUserFromObject(data);
        //BeeLog.d(TAG, user.getInstitutions().toString());
        setUpUserInfo();
        content.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
