package com.example.liranyehudar.socialnetworkforacademic.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liranyehudar.socialnetworkforacademic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFeedFragment extends Fragment {


    public HomeFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_home_feed, container, false);

        return view;
    }

}
