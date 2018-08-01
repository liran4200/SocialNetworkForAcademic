package com.example.liranyehudar.socialnetworkforacademic.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.liranyehudar.socialnetworkforacademic.Interface.Communicator;
import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFirstPageFragment extends Fragment {

    private Communicator communicator;
    private EditText edtFirstName;
    private EditText edtLastName;
    private EditText edtEmail;

    private Student student;

    public RegisterFirstPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (Communicator) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register_first_page, container, false);
        bindUI(view);
        student = (Student) this.getArguments().getSerializable("student");
        Button nextButton = view.findViewById(R.id.btn_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                communicator.update();

                student.setFirstName(edtFirstName.getText().toString());
                student.setLastName(edtLastName.getText().toString());
                student.setEmail(edtEmail.getText().toString());


                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new RegisterSecondPageFragment();
                Bundle parameters = new Bundle();
                parameters.putSerializable("student",student);
                fragment.setArguments(parameters);
                fragmentTransaction.replace(R.id.fragments_container,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private void bindUI(View view) {
        edtFirstName = view.findViewById(R.id.edit_first_name);
        edtLastName = view.findViewById(R.id.edit_last_name);
        edtEmail = view.findViewById(R.id.edit_email);
    }

}
