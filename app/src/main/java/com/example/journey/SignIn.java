package com.example.journey;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.journey.databinding.FragmentSignInBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


/**
 * The SignIn() method represents a sign in in button.
 */
public class SignIn extends Fragment {
  private UserViewModel logInData;

  private FragmentSignInBinding binding;
  private Button signInButton;
  private TextView createAccountText;
  private TextInputLayout emailTextInput;
  private TextInputLayout passwordTextInput;

  /**
   * The constructor for the SignIn class.
   */
  public SignIn() {
    super(R.layout.fragment_sign_in);
    // Required empty public constructor
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    binding = FragmentSignInBinding.inflate(inflater, container, false);
    logInData = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    signInButton = binding.signin;
    createAccountText = binding.createAccountText;
    emailTextInput = binding.email;
    passwordTextInput = binding.password;

    signInButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        System.out.println("CLICK!");
        logInData.getEmail().setValue(String.valueOf(Objects.requireNonNull(emailTextInput.getEditText()).getText()));
        logInData.getPassword().setValue(String.valueOf(Objects.requireNonNull(passwordTextInput.getEditText()).getText()));
      }
    });

    createAccountText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
       logInData.getShowCreateAccount().setValue(true);
      }
    });
  }
}