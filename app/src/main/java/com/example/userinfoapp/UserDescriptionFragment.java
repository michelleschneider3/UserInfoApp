package com.example.userinfoapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDescriptionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserDescriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserDescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserDescriptionFragment newInstance(String param1, String param2) {
        UserDescriptionFragment fragment = new UserDescriptionFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_description, container, false);

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editPhoneNumber = view.findViewById(R.id.editTextPhoneNumberInDescriptionFragment);
                EditText editDescription = view.findViewById(R.id.editTextDescription);
                TextView viewDescription = view.findViewById(R.id.textViewDescription);

                String phoneNumber = editPhoneNumber.getText().toString();
                String description = editDescription.getText().toString();
                String phoneNumberWithCountryCode = "+972" + phoneNumber;


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("users").child(phoneNumberWithCountryCode);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User value = dataSnapshot.getValue(User.class);
                        if (value != null) {
                            value.setDescription(description);
                            myRef.setValue(value);
                            editPhoneNumber.setText("");
                            editDescription.setText("");
                            viewDescription.setText("");
                        } else {
                            Toast.makeText(getContext(), "No user found with this phone number", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(getContext(), "Error loading data: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        Button showButton = view.findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editPhoneNumber = view.findViewById(R.id.editTextPhoneNumberInDescriptionFragment);
                TextView viewDescription = view.findViewById(R.id.textViewDescription);

                String phoneNumber = editPhoneNumber.getText().toString();
                String phoneNumberWithCountryCode = "+972" + phoneNumber;

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("users").child(phoneNumberWithCountryCode);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User value = dataSnapshot.getValue(User.class);
                        if (value != null) {
                            viewDescription.setText(value.getDescription());
                        } else {
                            Toast.makeText(getContext(), "No user found with this phone number", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(getContext(), "Error loading data: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return view;
    }
}