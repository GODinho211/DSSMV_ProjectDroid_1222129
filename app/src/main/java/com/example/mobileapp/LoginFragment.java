package com.example.mobileapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mobileapp.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private ImageView etlogo;
    private EditText etemail;
    private EditText etpassword;
    private Button register;
    private Button login;
    private FirebaseAuth mAuth;
    private User u;

    public LoginFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_login, container,false);

        mAuth = FirebaseAuth.getInstance();
         login = view.findViewById(R.id.LoginButton);
         register = view.findViewById(R.id.RegisterLoginButton);
         etemail = view.findViewById(R.id.EmailLogin);
         etpassword = view.findViewById(R.id.PasswordLogin);
         etlogo = view.findViewById(R.id.Logo);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                login();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });


        return view;
    }




    private void login() {
        mAuth.signInWithEmailAndPassword(u.getEmail(),u.getPassword())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_weatherFragment);
                    }
                });
    }

    private void getData() {
        u = new User();
        u.setEmail(etemail.getText().toString());
        u.setPassword(etpassword.getText().toString());

    }
}