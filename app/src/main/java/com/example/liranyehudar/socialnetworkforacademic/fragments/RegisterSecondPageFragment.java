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
public class RegisterSecondPageFragment extends Fragment {

    private Communicator communicator;
    private EditText editPassword;
    private EditText editConfirmPass;
    private Button nextButton;
    private Student student;

    public RegisterSecondPageFragment() {
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
        View view  = inflater.inflate(R.layout.fragment_register_second_page, container, false);
        bindUI(view);
        student = (Student) this.getArguments().getSerializable("student");
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = editPassword.getText().toString();
                String confirmPass = editConfirmPass.getText().toString();
                if(passwordValidation(password,confirmPass)) { // validation password
                    // miss encryption password and set student object

                    communicator.update();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new RegisterThirdPageFragment();
                    Bundle parameters = new Bundle();
                    parameters.putSerializable("student",student);
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
        nextButton = view.findViewById(R.id.btn_next_to_third);
        editPassword = view.findViewById(R.id.edit_password);
        editConfirmPass = view.findViewById(R.id.edit_confirm_password);
    }

    private boolean passwordValidation(String password,String confirmPass) {
        if(password.length() == 0 || confirmPass.length() == 0){
            Toast.makeText(getActivity().getApplicationContext(),"Please fill all fields",Toast.LENGTH_LONG).show();
            return false;
        }

        if(!password.equals(confirmPass)){
            Toast.makeText(getActivity().getApplicationContext(),"Passwords do not match,try again",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}
