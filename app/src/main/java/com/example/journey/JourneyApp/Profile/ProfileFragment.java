package com.example.journey.JourneyApp.Profile;

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

import com.example.journey.R;
import com.example.journey.JourneyApp.Settings.SettingsFragment;
import com.example.journey.databinding.FragmentProfileBinding;
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
    FragmentProfileBinding binding;
    View layoutInflater;

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
        //settingsTab = findByView(R.id.settingsTab);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        fragmentManager = getChildFragmentManager();
        showFragment(new ProfileToDoFragment());

        layoutInflater = inflater.inflate(R.layout.fragment_profile, container, false);
        settingsTab = layoutInflater.findViewById(R.id.settingsTabNav);
        settingsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsFragment();
            }
        });

        //showFragment(new SettingsFragment());
        return binding.getRoot();
    }

    /**
     * The openSettingsFragment() method opens
     * the settings fragment when the
     * settings tab button is pressed.
     */
    public void openSettingsFragment() {
        SettingsFragment settingsFragment = new SettingsFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.settingsLayout, settingsFragment).commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = binding.profileTab;
        tabLayout.addOnTabSelectedListener(this);
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.profile_fragment_container, fragment).commit();
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

                /*case 3:
                showFragment(new SettingsFragment());
                break;

                 */
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}