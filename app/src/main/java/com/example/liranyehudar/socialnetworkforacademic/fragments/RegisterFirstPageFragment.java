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
import android.widget.Toast;

import com.example.liranyehudar.socialnetworkforacademic.Interface.Communicator;
import com.example.liranyehudar.socialnetworkforacademic.R;
import com.example.liranyehudar.socialnetworkforacademic.logic.Student;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFirstPageFragment extends Fragment {

    public static final int MIN_PASS_LENGTH = 6;

    private Student student;
    private Communicator communicator;
    private EditText edtFirstName;
    private EditText edtLastName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPass;

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
                String pass = edtPassword.getText().toString();
                String confirmPass = edtConfirmPass.getText().toString();
                if(passwordValidation(pass,confirmPass)) {
                    communicator.update();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new RegisterSecondPageFragment();
                    Bundle parameters = new Bundle();
                    parameters.putSerializable("student", student);
                    fragment.setArguments(parameters);
                    fragmentTransaction.replace(R.id.fragments_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

        return view;
    }


    private void bindUI(View view) {
        edtFirstName = view.findViewById(R.id.edit_first_name);
        edtLastName = view.findViewById(R.id.edit_last_name);
        edtEmail = view.findViewById(R.id.edit_email);
        edtPassword = view.findViewById(R.id.edit_password);
        edtConfirmPass = view.findViewById(R.id.edit_confirm_password);
    }

    private boolean passwordValidation(String password,String confirmPass) {
        if(password.length() == 0 || confirmPass.length() == 0){
            Toast.makeText(getActivity().getApplicationContext(),"Please fill all fields",Toast.LENGTH_LONG).show();
            return false;
        }

        if(password.length()< MIN_PASS_LENGTH){
            Toast.makeText(getActivity().getApplicationContext(),"You have to enter a passowrd at least 6 letter",Toast.LENGTH_LONG).show();
            return false;
        }

        if(!password.equals(confirmPass)){
            Toast.makeText(getActivity().getApplicationContext(),"Passwords do not match,try again",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}
