package com.example.journey.Sticker.Framents;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.journey.Sticker.AuthenticationViewModel;
import com.example.journey.Sticker.Constants;
import com.example.journey.R;
import com.example.journey.Sticker.StickerAppDelegate;
import com.example.journey.databinding.FragmentCreateAccountBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class CreateAccount extends Fragment {
  private static final String TAG = "CreateAccountFragment";

  private AuthenticationViewModel logInData;
  private FragmentCreateAccountBinding binding;
  private TextView signInText;
  private Button createAccountButton;
  private TextInputLayout emailTextInput;
  private TextInputLayout passwordTextInput;
  StickerAppDelegate delegate;

  /**
   * The constructor for the CreateAccount class.
   */
  public CreateAccount() {
    super(R.layout.fragment_create_account);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = this.getArguments();
    assert bundle != null;
    delegate = bundle.getParcelable(Constants.DELEGATE);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    binding = FragmentCreateAccountBinding.inflate(inflater, container, false);

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
        String email = String.valueOf(Objects.requireNonNull(emailTextInput.getEditText()).getText());
        String password = String.valueOf(Objects.requireNonNull(passwordTextInput.getEditText()).getText());

        delegate.createNewUserWith(email, password);
        Log.i(TAG, "CREATE ACCOUNT BUTTON CLICKED");

      }
    });



    signInText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        delegate.alreadyHaveAnAccountWasClicked();
        Log.i(TAG, "ALREADY HAVE ACCOUNT TEXT CLICKED");
      }
    });

  }

  @Override
  public void onStop() {
    super.onStop();
    clearTextEdit();
  }

  public void clearTextEdit() {
    emailTextInput.getEditText().getText().clear();
    passwordTextInput.getEditText().getText().clear();

    emailTextInput.getEditText().clearFocus();
    passwordTextInput.getEditText().clearFocus();
  }
}