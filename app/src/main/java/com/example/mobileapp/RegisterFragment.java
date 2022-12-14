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
import android.widget.Toast;
import com.example.mobileapp.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




public class RegisterFragment extends Fragment {
    private ImageView etlogo;
    private EditText etname;
    private EditText etemail;
    private EditText etpassword;
    private Button register;
    private FirebaseAuth mAuth;
    private User u;


    public RegisterFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_register, container,false);

        etlogo= (ImageView) view.findViewById(R.id.imageView2);
        etemail= view.findViewById(R.id.EmailRegister);
        etname= view.findViewById(R.id.NameRegister);
        etpassword= view.findViewById(R.id.PasswordRegister);
        register = view.findViewById(R.id.RegisterButton);
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();
                createlogin();
            }
        });
        return view;
    }

    private void createlogin() {
        mAuth.createUserWithEmailAndPassword(u.getEmail(),u.getPassword()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser user = mAuth.getCurrentUser();
                u.setId(user.getUid());
                u.saveData();
                Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

    }


    private void getdata() {
            if (etname.getText().toString() == "" || etemail.getText().toString() == "" || etpassword.getText().toString() == "") {
            Toast.makeText(getContext(), "deve preencher todos so parametros", Toast.LENGTH_LONG).show();
            } else {
                u = new User();
                u.setName(etname.getText().toString());
                u.setEmail(etemail.getText().toString());
                u.setPassword(etpassword.getText().toString());


            }
        }
    }


