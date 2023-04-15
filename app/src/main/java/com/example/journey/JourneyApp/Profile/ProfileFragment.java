package com.example.journey.JourneyApp.Profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.journey.JourneyApp.Profile.Modals.AddApplicationModal;
import com.example.journey.JourneyApp.Profile.Modals.UpdateApplicationModal;
import com.example.journey.JourneyApp.Settings.Settings;
import com.example.journey.R;
import com.example.journey.databinding.FragmentProfileBinding;
import com.example.journey.databinding.ProfileTopMenuLayoutBinding;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements TabLayout.OnTabSelectedListener {

  Button settingsTab;
  FragmentManager fragmentManager;
  TabLayout tabLayout;
  Button addNewApplication;
  FragmentProfileBinding binding;
  View layoutInflater;
  Activity activity;

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
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment\
    binding = FragmentProfileBinding.inflate(inflater, container, false);

    fragmentManager = getChildFragmentManager();
    showFragment(new ProfileToDoFragment());

    activity = getActivity();

    //showFragment(new SettingsFragment());
    return binding.getRoot();
  }



  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    tabLayout = binding.profileTab;
    addNewApplication = binding.addNewApplicationButton;
    tabLayout.addOnTabSelectedListener(this);

    ProfileTopMenuLayoutBinding topMenu = binding.includeProfileMenu;

    addNewApplication.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openAddApplicationModal();
//                openUpdateApplicationModal();
      }
    });
  }

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

  /**
   * The onStart() method opens
   * the settings activity  when the
   * settings button is pressed.
   */
  public void onStart() {
    super.onStart();

    settingsTab = (Button) activity.findViewById(R.id.settingsTabNav);
    settingsTab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Intent i = new Intent(activity, Settings.class);
        startActivity(new Intent(activity, Settings.class));
      }
    });
  }

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

  @Override
  public void onTabUnselected(TabLayout.Tab tab) {

  }

  @Override
  public void onTabReselected(TabLayout.Tab tab) {

  }
}