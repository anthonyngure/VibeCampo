package com.vibecampo.android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.RequestParams;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.vibecampo.android.R;
import com.vibecampo.android.activity.BaseActivity;
import com.vibecampo.android.activity.FragmentActivity;
import com.vibecampo.android.network.BackEnd;
import com.vibecampo.android.network.Client;
import com.vibecampo.android.network.Meta;
import com.vibecampo.android.settings.PrefUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ke.co.toshngure.basecode.networking.ResponseHandler;
import ke.co.toshngure.basecode.utils.BaseUtils;

/**
 * Created by Anthony Ngure on 01/08/2017.
 * Email : anthonyngure25@gmail.com.
 * Company : VibeCampo Social Network..
 */

public class SignInFragment extends BaseFragment {

    public static final String TAG = SignInFragment.class.getSimpleName();
    @BindView(R.id.emailMET)
    MaterialEditText emailMET;
    @BindView(R.id.passwordMET)
    MaterialEditText passwordMET;
    private Unbinder unbinder;

    public static SignInFragment newInstance() {
        Bundle args = new Bundle();
        SignInFragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //((BaseActivity) context).getToolbar().setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        unbinder = ButterKnife.bind(this, view);
        emailMET.addValidator(BaseUtils.createRequiredValidator("This field is required"));
        BaseUtils.cacheInput(emailMET, R.string.pref_email, PrefUtils.getInstance());
        passwordMET.addValidator(BaseUtils.createRequiredValidator("This field is required"));
        BaseUtils.cacheInput(passwordMET, R.string.pref_password, PrefUtils.getInstance());
        return view;
    }

    @OnClick(R.id.passwordRecoveryBtn)
    void recoverPassword(){
        ((BaseActivity) getActivity()).openWebsite("https://www.vibecampo.com/forgot-password");
    }

    @OnClick(R.id.signInBtn)
    void signIn() {
        if (emailMET.validate() && passwordMET.validate()) {
            connect();
        }
    }

    @OnClick(R.id.signUpBtn)
    void signBtn() {
        FragmentActivity.start(getActivity(), SignUpFragment.newInstance(),
                getString(R.string.sign_up), null);
        getActivity().finish();
    }

    @Override
    public void connect() {
        RequestParams params = new RequestParams();
        params.put(BackEnd.Params.USERNAME, emailMET.getText().toString());
        params.put(BackEnd.Params.PASSWORD, passwordMET.getText().toString());
        Client.getInstance().getClient().post(getActivity(),
                Client.absoluteUrl(BackEnd.EndPoints.SIGN_IN),
                params, new ResponseHandler(this));
    }

    @Override
    protected void onSuccessResponse(JSONObject data, Meta meta) {
        super.onSuccessResponse(data, meta);
        ((BaseActivity) getActivity()).onAuthSuccessful(data, meta);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
