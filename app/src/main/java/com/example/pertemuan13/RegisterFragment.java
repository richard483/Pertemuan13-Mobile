package com.example.pertemuan13;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {
    EditText register_email_et;
    EditText register_password_et;
    EditText register_reenterPassword_et;
    Button register_bt;
    FirebaseAuth mAuth;
    String email;
    String password;
    String reenterpassword;

    View.OnClickListener registerUser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            email = register_email_et.getText().toString();
            password = register_password_et.getText().toString();
            reenterpassword = register_reenterPassword_et.getText().toString();

            if (checker()){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getActivity(), "Berhasi membuat akun!", Toast.LENGTH_SHORT).show();
                                    getParentFragmentManager().popBackStack();
                                } else{
                                    Toast.makeText(getActivity(), "Error karena:\n" + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        register_email_et = view.findViewById(R.id.register_email_et);
        register_password_et = view.findViewById(R.id.register_password_et);
        register_reenterPassword_et = view.findViewById(R.id.register_reenterPassword_et);
        register_bt = view.findViewById(R.id.register_bt);

        mAuth = FirebaseAuth.getInstance();

        register_bt.setOnClickListener(registerUser);

    }

    public boolean checker(){
        boolean flag = true;
        String error = "Cannot register because error:\n";

        if(email.isEmpty()){
            flag = false;
            error = error + "- email is empty\n";
        }
        if(password.isEmpty()){
            flag = false;
            error = error + "- password is empty\n";
        }
        if(reenterpassword.isEmpty()){
            flag = false;
            error = error + "- reenterpassword is empty\n";
        }
        if(!password.equals(reenterpassword)){
            flag = false;
            error = error + "- password and reenter password is not equals\n";
        }

        if(flag == false){
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        }

        return flag;
    }
}