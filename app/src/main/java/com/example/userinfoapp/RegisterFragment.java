package com.example.userinfoapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        Button registerButtonInRegisterFragment = view.findViewById(R.id.registerButtonInRegisterFragment);
        registerButtonInRegisterFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editUserName = view.findViewById(R.id.editTextUserNameInRegisterFragment);
                EditText editPassword = view.findViewById(R.id.editTextPasswordInRegisterFragment);
                EditText editPhoneNumber = view.findViewById(R.id.editTextPhoneNumberInRegisterFragment);
                EditText editAddress = view.findViewById(R.id.editTextAddressInRegisterFragment);

                String userName = editUserName.getText().toString().trim();
                String password = editPassword.getText().toString();
                String phoneNumber = editPhoneNumber.getText().toString();
                String address = editAddress.getText().toString();

                if (userName.isEmpty() || password.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all the required fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (passwordValidation(password)) {
                    if (phoneNumberValidation(phoneNumber)) {
                        String fakeEmail = userName + "@myapp.com";
                        String phoneNumberWithCountryCode = addCountryCode(phoneNumber);
                        final String finalPhoneNumber = phoneNumberWithCountryCode;

                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(fakeEmail, password)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("users");

                                        User user = new User(fakeEmail, password, finalPhoneNumber, address);
                                        myRef.child(finalPhoneNumber).setValue(user);

                                        Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_userDescriptionFragment);
                                    } else {
                                        Toast.makeText(getContext(), "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Toast.makeText(getContext(), "The phone number must be at least 8 digits long", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private static boolean passwordValidation (String password) {
        return (password.length() >= 6) ;
    }

    private static boolean phoneNumberValidation (String phoneNumber){
            return (phoneNumber.length() >= 8);
    }

    private static String addCountryCode (String phoneNumber) {
        if (phoneNumber.startsWith("+972")) {
            return phoneNumber;
        } else {
            return "+972" + phoneNumber;
        }
    }
}