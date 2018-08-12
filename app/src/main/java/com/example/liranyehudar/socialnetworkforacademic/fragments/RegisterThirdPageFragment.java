package com.example.liranyehudar.socialnetworkforacademic.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.liranyehudar.socialnetworkforacademic.Interface.Communicator;
import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;

import java.util.ArrayList;
import java.util.Arrays;

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
    Student student;

    private Spinner spinnerAcademicInstitution;
    private Spinner spinnerStudies;
    private Spinner spinnerYears;
    private Button btnNext;
    private Communicator communicator;

    public RegisterThirdPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (Communicator)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register_third_page, container, false);
        bindUI(view);
        student = (Student) this.getArguments().getSerializable("student");
        final String pass = (String) this.getArguments().get("password");
        final String email = (String) this.getArguments().get("email");

        ArrayList<String> listAcademic = new ArrayList<String>(Arrays.asList(academic));
        initSpinner(listAcademic , spinnerAcademicInstitution);

        ArrayList<String> listStudies = new ArrayList<String>(Arrays.asList(studies));
        initSpinner(listStudies , spinnerStudies);

        ArrayList<String> listYears = new ArrayList<String>(Arrays.asList(years));
        initSpinner(listYears, spinnerYears);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.update();

                student.setAcademic(spinnerAcademicInstitution.getSelectedItem().toString());
                student.setYear(spinnerYears.getSelectedItem().toString());
                student.setField(spinnerStudies.getSelectedItem().toString());

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new RegisterFourthPageFragment();
                Bundle parameters = new Bundle();
                parameters.putSerializable("student",student);
                parameters.putString("password",pass);
                parameters.putString("email",email);
                fragment.setArguments(parameters);
                fragmentTransaction.replace(R.id.fragments_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void bindUI(View v) {
        spinnerAcademicInstitution = v.findViewById(R.id.spinner_academic);
        spinnerStudies = v.findViewById(R.id.spinner_studies);
        spinnerYears = v.findViewById(R.id.spinner_year);
        btnNext = v.findViewById(R.id.btn_next_select_courses);
    }

    private void initSpinner(ArrayList<String> list, Spinner sp) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
        sp.setAdapter(adapter);
    }

}
