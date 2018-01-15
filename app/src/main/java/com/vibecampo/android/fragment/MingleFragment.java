package com.vibecampo.android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vibecampo.android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MingleFragment extends Fragment {


    public MingleFragment() {
        // Required empty public constructor
    }

    public static MingleFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MingleFragment fragment = new MingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

}
