package com.example.journey.JourneyApp.Profile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Main.Helper;
import com.example.journey.JourneyApp.Profile.Modals.AddApplicationModal;
import com.example.journey.JourneyApp.Profile.Modals.UpdateApplicationModal;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.JourneyApp.Signup.SignUp;
import com.example.journey.JourneyApp.Settings.SettingsFragment;
import com.example.journey.Sticker.Constants;
import com.example.journey.JourneyApp.Settings.Settings;
import com.example.journey.R;
import com.example.journey.databinding.FragmentProfileBinding;
import com.example.journey.databinding.ProfileDetailsBinding;
import com.example.journey.databinding.ProfileTopMenuLayoutBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    String TAG = ProfileFragment.class.toGenericString();
    private final Integer SELECT_IMAGE = 33;
    Button settingsTab;
    FragmentManager fragmentManager;
    TabLayout tabLayout;
    Button addNewApplication;
    FragmentProfileBinding binding;
    View layoutInflater;
    FirebaseUser currentUser;
    UserModel currentUserModel;
    ActivityResultLauncher<Intent> pickerLauncher;
    ActivityResultLauncher<Intent> captureLauncher;

    ShapeableImageView profilePicture;
    TextView followers;
    TextView following;
    TextView userProfileName;
    /*
    String text = getString(R.string.FORMAT_STRING, ARGUMENTS);

     */

    // Listeners
    ValueEventListener userEventListener;

  public ProfileFragment() {
    super(R.layout.fragment_profile);
  }

  public static ProfileFragment newInstance(String param1, String param2) {
      ProfileFragment fragment = new ProfileFragment();
      Bundle args = new Bundle();
      return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      pickerLauncher = registerForActivityResult(
              new ActivityResultContracts.StartActivityForResult(),
              result -> {
                  if (result == null) {
                      Helper.showToast(getContext(), "FAILED TO OPEN PICKER");
                      return;
                  }

                  if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                      Intent intent = result.getData();
                      Uri filePath = intent.getData();
                      uploadImageToStorage(filePath);
                  } else {
                      Helper.showToast(getContext(), "INVALID RESULT CODE OR DATA IS NULL");
                  }
              }
      );

      captureLauncher = registerForActivityResult(
              new ActivityResultContracts.StartActivityForResult(),
              result -> {
                  if (result == null) {
                      Helper.showToast(getContext(), "FAILED TO OPEN PICKER");
                      return;
                  }

                  if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                      Intent intent = result.getData();
                          Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                          uploadImageToStorage(bitmap);
                  } else {
                      Helper.showToast(getContext(), "INVALID RESULT CODE OR DATA IS NULL");
                  }
              }
      );
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
      binding = FragmentProfileBinding.inflate(inflater, container, false);
      fragmentManager = getChildFragmentManager();
      showFragment(new ProfileToDoFragment());
      return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

  tabLayout = binding.profileTab;
  addNewApplication = binding.addNewApplicationButton;
  tabLayout.addOnTabSelectedListener(this);

  ProfileTopMenuLayoutBinding topMenu = binding.includeProfileMenu;
  ProfileDetailsBinding profileDetailsBinding = binding.includeProfileDetails;

  profilePicture = profileDetailsBinding.profilePicture;
  followers = profileDetailsBinding.followersCount;
  following = profileDetailsBinding.followingCount;
  userProfileName = profileDetailsBinding.userProfileName;

  currentUser = Database.FIREBASE_AUTH.getCurrentUser();

  userEventListener = new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
          currentUserModel = snapshot.getValue(UserModel.class);
          updateProfileView();
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
      }
  };

//        Database.DB_REFERENCE.child(Database.USERS).child(currentUser.getUid()).addValueEventListener(userEventListener);
    Database.DB_REFERENCE.child(Database.USERS).child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task) {
            if (task.isSuccessful()) {
                DataSnapshot results = task.getResult();
                currentUserModel = results.getValue(UserModel.class);
                updateProfileView();
            }
        }
    });

    settingsTab = topMenu.settingsTabNav;
    settingsTab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openSettingsFragment();
        }
    });

    addNewApplication.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openAddApplicationModal();
        }
    });

    profilePicture.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pickProfilePictureTapped();
        }
    });
}

  // Opening Fragments
  public void showFragment(Fragment fragment) {
      FragmentTransaction transaction = fragmentManager.beginTransaction();
      transaction.replace(R.id.profile_fragment_container, fragment).commit();
  }

  public void openAddApplicationModal() {
      AddApplicationModal addApplicationModal = new AddApplicationModal();
      addApplicationModal.show(getChildFragmentManager(), AddApplicationModal.TAG);
  }

  public void openUpdateApplicationModal() {
      UpdateApplicationModal updateApplicationModal = new UpdateApplicationModal();
      updateApplicationModal.show(getChildFragmentManager(), UpdateApplicationModal.TAG);
  }

  public void openSettingsFragment() {
      SettingsFragment settingsFragment = new SettingsFragment();
      FragmentTransaction transaction = fragmentManager.beginTransaction();

      transaction.replace(R.id.settingsLayout, settingsFragment).commit();
  }

  // Updating Profile View
  public void updateProfileView() {

      String fullName = currentUserModel.getFirstName() + " " + currentUserModel.getLastName();
      userProfileName.setText(getString(R.string.user_name_format, fullName));
      following.setText(String.valueOf(currentUserModel.getFollowing()));
      followers.setText(String.valueOf(currentUserModel.getFollowers()));

      if (currentUserModel.getProfileImage() == null) {
          profilePicture.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.pick_photo));
      } else {
          StorageReference profileURL = Database.DB_STORAGE_REFERENCE.child(currentUserModel.getProfileImage());
          Glide.with(requireActivity()).load(profileURL).into(profilePicture);
          profilePicture.setClickable(false);
      }
  }

  // Updating Profile Picture
  public void pickProfilePictureTapped() {
      AlertDialog builder = new MaterialAlertDialogBuilder(getContext())
              .setTitle("Add profile picture")
              .setMessage("Select the method you would prefer to add a picture to your profile.")
              .setNeutralButton(getContext().getResources().getString(R.string.gallery), new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      selectImage();
                  }
              })
              .setNegativeButton(getContext().getResources().getString(R.string.camera), new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      openImageCapture();
                  }
              })
              .show();
  }

  public void selectImage() {
      Intent intent = new Intent();
      intent.setType("image/*");
      intent.setAction(Intent.ACTION_PICK);
      pickerLauncher.launch(intent);
  }

  private void openImageCapture() {
      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      if (intent.resolveActivity(getContext().getPackageManager()) != null) {
          captureLauncher.launch(intent);
      }
  }

  private void uploadImageToStorage(Uri filepath) {
      String filename = createFileName();
      StorageReference storageReference = Database.DB_STORAGE_REFERENCE.child(filename);

      storageReference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
              Log.i(TAG, "IMAGE SUCCESSFULLY UPLOADED TO FIREBASE");
              try {
                  Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filepath);
                  profilePicture.setImageBitmap(bitmap);
                  saveUserProfilePicture(filename);
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              Log.e(TAG, "FAILED TO UPLOAD IMAGE TO FIREBASE");
          }
      });
  }

  private void uploadImageToStorage(Bitmap bitmap) {
      String filename = createFileName();
      StorageReference storageReference = Database.DB_STORAGE_REFERENCE.child(filename);

      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
      byte[] bytes = byteArrayOutputStream.toByteArray();

      storageReference.putBytes(bytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
              Log.i(TAG, "IMAGE SUCCESSFULLY UPLOADED TO FIREBASE");
                  profilePicture.setImageBitmap(bitmap);
                  saveUserProfilePicture(filename);
          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              Log.e(TAG, "FAILED TO UPLOAD IMAGE TO FIREBASE");
          }
      });
  }

  private String createFileName() {
      return "profile_" + currentUser.getUid() + "_" + UUID.randomUUID().toString();
  }

  private void saveUserProfilePicture(String filename) {
      Database.DB_REFERENCE.child(Database.USERS).child(currentUser.getUid()).runTransaction(new Transaction.Handler() {
          @NonNull
          @Override
          public Transaction.Result doTransaction(@NonNull MutableData currentData) {
              UserModel userModel = currentData.getValue(UserModel.class);

              if (userModel != null) {
                  userModel.setProfileImage(filename);
                  currentData.setValue(userModel);
              }
              return Transaction.success(currentData);
          }

          @Override
          public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
              Log.e(TAG, "FAILED TO UPDATE USER WITH NEW PROFILE PICTURE");
          }
      });
  }

  // Profile Tab Menu
  @Override
  public void onTabSelected(TabLayout.Tab tab) {
      switch (tab.getPosition()) {
          case 0:
              showFragment(new ProfileToDoFragment());
              break;
          case 1:
              showFragment(new ProfileTimelineFragment());
              break;
          case 2:
              showFragment(new ProfileDocumentsFragment());
              break;
      }
  }
}

@Override
public void onTabUnselected(TabLayout.Tab tab) {

}

@Override
public void onTabReselected(TabLayout.Tab tab) {

}
}