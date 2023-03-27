package com.example.journey.JourneyApp.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.journey.R;
import com.example.journey.databinding.FragmentTimelineBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalDateTime;
import java.util.Calendar;

public class ProfileTimelineFragment extends Fragment {
    FragmentTimelineBinding binding;
    EditText datePicker;
    EditText timePicker;
    public ProfileTimelineFragment() {
        super(R.layout.fragment_timeline);
    }

    public static ProfileTimelineFragment newInstance(String param1, String param2) {
        ProfileTimelineFragment fragment = new ProfileTimelineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTimelineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        datePicker = binding.dateInput.getEditText();
        timePicker = binding.timeInput.getEditText();

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker();
            }
        });

    }

    public void openDatePicker() {
        Calendar calendar = Calendar.getInstance();

        MaterialDatePicker datePicker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        datePicker.show(this.getChildFragmentManager(), datePicker.toString());


    }

    public void openTimePicker() {
        LocalDateTime now = LocalDateTime.now();

        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(now.getHour())
                .setMinute(now.getMinute())
                .setTitleText("Select Time")
                .build();
        timePicker.show(this.getChildFragmentManager(), timePicker.toString());
    }
}