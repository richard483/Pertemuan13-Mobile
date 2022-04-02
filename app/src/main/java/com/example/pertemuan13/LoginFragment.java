package com.example.pertemuan13;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    EditText login_email_et;
    EditText password_email_et;
    Button login_button;
    TextView toRegister_tv;
    FirebaseAuth mAuth;
    String password;
    String email;

    View.OnClickListener toRegister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container_main, new RegisterFragment())
                    .addToBackStack(null).commit();
        }
    };

    View.OnClickListener toHome = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            password = password_email_et.getText().toString();
            email = login_email_et.getText().toString();
            if(checker()){
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Berhasil melakukan login!\nMenuju Home menu", Toast.LENGTH_SHORT).show();
                            Intent tomain = new Intent(getActivity(), HomeActivity.class);
                            startActivity(tomain);
                        }else{
                            Toast.makeText(getActivity(), "Error:\n" + task.getException(), Toast.LENGTH_SHORT).show();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        login_email_et = view.findViewById(R.id.login_email_et);
        password_email_et = view.findViewById(R.id.login_password_et);
        login_button = view.findViewById(R.id.login_bt);
        toRegister_tv = view.findViewById(R.id.toRegister_tv);

        mAuth = FirebaseAuth.getInstance();

        toRegister_tv.setOnClickListener(toRegister);
        login_button.setOnClickListener(toHome);

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

        if(flag == false){
            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        }

        return flag;
    }
}