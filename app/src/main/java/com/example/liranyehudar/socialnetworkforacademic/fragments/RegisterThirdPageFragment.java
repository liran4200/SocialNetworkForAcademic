package com.example.liranyehudar.socialnetworkforacademic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.liranyehudar.socialnetworkforacademic.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterThirdPageFragment extends Fragment {

    String[] academic = {"Afeka"};
    String[] studies = {"Software engineering",
            "Mechanical engineering",
            "Electrical engineering",
            "Industrial engineering",
            "Biomedical engineering"};

    String[] years ={"First year","Second year","Third year","Fourth year"};

    Spinner spinnerAcademicInstitution;
    Spinner spinnerStudies;
    Spinner spinnerYears;

    public RegisterThirdPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register_third_page, container, false);
        bindUI(view);
        ArrayList<String> listAcademic = new ArrayList<String>(Arrays.asList(academic));
        initSpinner(listAcademic , spinnerAcademicInstitution);

        ArrayList<String> listStudies = new ArrayList<String>(Arrays.asList(studies));
        initSpinner(listStudies , spinnerStudies);

        ArrayList<String> listYears = new ArrayList<String>(Arrays.asList(years));
        initSpinner(listYears, spinnerYears);

        return view;
    }

    private void bindUI(View v) {
        spinnerAcademicInstitution = v.findViewById(R.id.spinner_academic);
        spinnerStudies = v.findViewById(R.id.spinner_studies);
        spinnerYears = v.findViewById(R.id.spinner_year);
    }

    private void initSpinner(ArrayList<String> list, Spinner sp) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
        sp.setAdapter(adapter);
    }

}
