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

import org.json.JSONObject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ke.co.toshngure.basecode.networking.ResponseHandler;
import ke.co.toshngure.basecode.utils.BaseUtils;

/**
 * Created by Anthony Ngure on 06/09/2017.
 * Email : anthonyngure25@gmail.com.
 * Company : VibeCampo Social Network..
 */

public class SignUpFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.nameMET)
    MaterialEditText nameMET;
    @BindView(R.id.emailMET)
    MaterialEditText emailMET;
    @BindView(R.id.phoneMET)
    MaterialEditText phoneMET;
    @BindView(R.id.passwordMET)
    MaterialEditText passwordMET;
    @BindView(R.id.confirmPasswordMET)
    MaterialEditText confirmPasswordMET;
    @BindString(R.string.error_field_required)
    String errorFieldRequired;


    public static SignUpFragment newInstance() {

        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        unbinder = ButterKnife.bind(this, view);
        nameMET.addValidator(BaseUtils.createRequiredValidator(errorFieldRequired));
        emailMET.addValidator(BaseUtils.createRequiredValidator(errorFieldRequired))
                .addValidator(BaseUtils.createEmailValidator("You must use a valid email address"));
        phoneMET.addValidator(BaseUtils.createRequiredValidator(errorFieldRequired))
                .addValidator(BaseUtils.createLengthValidator(10));
        passwordMET.addValidator(BaseUtils.createRequiredValidator(errorFieldRequired));
        confirmPasswordMET.addValidator(BaseUtils.createRequiredValidator(errorFieldRequired));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.signUpBtn)
    public void onViewClicked(View view) {

        if (nameMET.validate() && emailMET.validate() && phoneMET.validate()
                && passwordMET.validate() && confirmPasswordMET.validate()) {
            if (passwordMET.getText().toString().equals(confirmPasswordMET.getText().toString())) {
                connect();
            } else {
                toast("Passwords do no match!");
            }
        }
    }


    @OnClick(R.id.signInBtn)
    void signBtn() {
        FragmentActivity.start(getActivity(), SignInFragment.newInstance(),
                getString(R.string.sign_up), null);
        getActivity().finish();
    }

    @Override
    public void connect() {
        super.connect();
        RequestParams params = new RequestParams();
        params.put(BackEnd.Params.NAME, nameMET.getText().toString());
        params.put(BackEnd.Params.EMAIL, emailMET.getText().toString());
        params.put(BackEnd.Params.PHONE, phoneMET.getText().toString());
        params.put(BackEnd.Params.PASSWORD, passwordMET.getText().toString());
        Client.getInstance().getClient().post(getActivity(),
                Client.absoluteUrl(BackEnd.EndPoints.SIGN_UP),
                params, new ResponseHandler(this));
    }



    @Override
    protected void onSuccessResponse(JSONObject data, Meta meta) {
        super.onSuccessResponse(data, meta);
        ((BaseActivity) getActivity()).onAuthSuccessful(data, meta);
    }
}
