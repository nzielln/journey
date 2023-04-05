package com.example.journey.JourneyApp.Profile.Modals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Profile.Models.ApplicationModel;
import com.example.journey.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;

public class AddApplicationModal extends BottomSheetDialogFragment {
    Button cancelButton;
    Button addApplicationButton;
    EditText datePicker;
    EditText timePicker;
    TextInputLayout sponsorField;
    AutoCompleteTextView typeDropDown;
    public static String TAG =  AddApplicationModal.class.toGenericString();
    DatabaseReference databaseReference;
    ApplicationModel applicationModel;
    String[] types = {"Banana", "Apple", "Strawberry", "Kiwi"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.add_application_modal, container, false);
        setUpDatabase();

        cancelButton = view.findViewById(R.id.cancel_button);
        addApplicationButton = view.findViewById(R.id.add_application);
        sponsorField = view.findViewById(R.id.sponsor_input);
        typeDropDown = view.findViewById(R.id.type_edit_text);

        ArrayAdapter<String> dropdownInputAdapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_input_item, types);
        typeDropDown.setThreshold(3);
        typeDropDown.setAdapter(dropdownInputAdapter);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                                "Cancel button clicked", Toast.LENGTH_SHORT)
                        .show();
                dismiss();
            }
        });

        addApplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSampleToDatabase();
                Toast.makeText(getActivity(),
                                "Add task button clicked", Toast.LENGTH_SHORT)
                        .show();
                dismiss();
            }
        });

        TextInputLayout datePickerInput = view.findViewById(R.id.date_input);
        TextInputLayout timePickerInput = view.findViewById(R.id.time_input);

        datePicker = datePickerInput.getEditText();
        timePicker = timePickerInput.getEditText();

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

        return view;
    }

    public void setUpDatabase() {
        databaseReference = FirebaseDatabase.getInstance(Database.JOURNEYDB).getReference();
    }

    public void addSampleToDatabase() {
        databaseReference.child(Database.APPLICATION).child(UUID.randomUUID().toString()).setValue("Something");
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

    public void addApplication() {

    }
}