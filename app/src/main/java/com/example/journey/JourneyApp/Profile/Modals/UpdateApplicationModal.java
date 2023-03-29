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

import com.example.journey.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.util.Calendar;

public class UpdateApplicationModal extends BottomSheetDialogFragment {
    Button cancelButton;
    Button updatepplicationButton;
    EditText datePicker;
    EditText timePicker;
    TextInputLayout statusField;
    AutoCompleteTextView applicationsOptions;
    public static String TAG =  UpdateApplicationModal.class.toGenericString();
    String[] applications = {"Banana", "Apple", "Strawberry", "Kiwi"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.update_application_modal, container, false);

        cancelButton = view.findViewById(R.id.cancel_button);
        updatepplicationButton = view.findViewById(R.id.update_application_button);
        statusField = view.findViewById(R.id.status_input);
        applicationsOptions = view.findViewById(R.id.application_name_options);

        ArrayAdapter<String> dropdownInputAdapter = new ArrayAdapter<String>(getContext(), R.layout.application_name_item, applications);
        applicationsOptions.setThreshold(3);
        applicationsOptions.setAdapter(dropdownInputAdapter);
        applicationsOptions.setText(applications[0], false);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                                "Cancel button clicked", Toast.LENGTH_SHORT)
                        .show();
                dismiss();
            }
        });

        updatepplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
