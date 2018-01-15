/*
 * Copyright (c) 2016. VibeCampo Social Network
 *
 * Website : http://www.vibecampo.com
 */

package com.vibecampo.android.network;

/**
 * Created by Tosh on 6/10/2016.
 */
public class BackEnd {

    public static final String BASE_URL = "https://www.grindon.world/api";
    public static final String URL = "https://www.vibecampo.com";


    public static final class QUERY {
        public static final String POST_VIBE = "postVibe";
        public static final String GET_VIBES = "getVibes";
        public static final String GET_VIBE_COMMENTS = "getVibeComments";
        public static final String GET_GOALS_TAB = "getGoalsTab";
        public static final String POST_VIBE_LIKE_UNLIKE = "postVibeLikeUnlike";
        public static final String GET_COMMUNITIES_TAB = "getCommunitiesTab";
        public static final String GET_NOTIFICATIONS = "getNotifications";
        public static final String GET_USER_COMMUNITIES = "getUserCommunities";
        public static final String ADD_GOAL = "addGoal";
    }

    public static final class EndPoints {
        public static final String SIGN_IN = "/Login";
        public static final String SIGN_UP = "/Signup";
        public static final String REQUEST = "/Request";
        public static final String POST = "/Post";
    }

    public static final class Response {
        public static final String META = "meta";
        public static final String DATA = "data";
        public static final String MESSAGE = "message";
        public static final int SUCCESS_CODE = 200;
        public static final int ERROR_CODE = 200;
    }

    public class Params {

        public static final String PHONE = "phone";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String USERNAME = "username";
        public static final String APP_KEY = "?appkey=";
        public static final String QUERY = "query";
        public static final String LIMIT = "limit";
        public static final String PURPOSE = "purpose";
        public static final String TEXT = "text";
        public static final String TITLE = "title";
        public static final String TO_COMMUNITY = "to_community";
        public static final String RECIPIENT_ID = "recipient_id";
        public static final String PHOTOS = "photos[]";
        public static final String PUBLISHER_ID = "publisher_id";
        public static final String TYPE = "type";
        public static final String STORY_TYPE = "story_type";
        public static final String GOAL_ID = "goal_id";
        public static final String VIBE_ID = "vibe_id";
        public static final String MY_GOALS = "my_goals";
        public static final String GOAL_NAME = "goal_name";
        public static final String GOAL_DESCRIPTION = "goal_description";
        public static final String COMMUNITY_ID = "community_id";
    }
}
