package com.example.journey.Framents;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
import com.example.journey.databinding.FragmentSignInBinding;
import com.google.android.material.textfield.TextInputLayout;


/**
 * The SignIn() method represents a sign in in button.
 */
public class SignIn extends Fragment {
  private static final String TAG = "SignInFragment";
  private AuthenticationViewModel logInData;
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

  StickerAppDelegate delegate;


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
    // Inflate the layout for this fragment
    binding = FragmentSignInBinding.inflate(inflater, container, false);
    logInData = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);

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
        String email = String.valueOf(emailTextInput.getEditText().getText());
        String password = String.valueOf(passwordTextInput.getEditText().getText());
        delegate.signInUserWith(email, password);
        Log.i(TAG, "SIGN IN BUTTON CLICKED");

      }
    });

    createAccountText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        delegate.createNewAccountWasClicked();
        Log.i(TAG, "CREATE NEW ACCOUNT TEXT CLICKED");
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