package com.example.journey;

import android.content.Intent;
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

import com.example.journey.databinding.FragmentCreateAccountBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class CreateAccount extends Fragment {
  private UserViewModel logInData;
  private FragmentCreateAccountBinding binding;
  private TextView signInText;
  private Button createAccountButton;
  private TextInputLayout emailTextInput;
  private TextInputLayout passwordTextInput;

  /**
   * The constructor for the CreateAccount class.
   */
  public CreateAccount() {
    super(R.layout.fragment_create_account);
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    binding = FragmentCreateAccountBinding.inflate(inflater, container, false);
    logInData = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    signInText = binding.haveAnAccountText;
    createAccountButton = binding.createAccount;
    emailTextInput = binding.email;
    passwordTextInput = binding.password;

    createAccountButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        logInData.getEmail().setValue(String.valueOf(Objects.requireNonNull(emailTextInput.getEditText()).getText()));
        logInData.getPassword().setValue(String.valueOf(Objects.requireNonNull(emailTextInput.getEditText()).getText()));
        CreateAccount.this.startActivity(new Intent(getActivity(), ProfileMessage.class));
      }
    });

    signInText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        logInData.getShowCreateAccount().setValue(false);
      }
    });

  }
}