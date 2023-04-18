package com.example.journey.JourneyApp.Profile.Modals;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Main.Helper;
import com.example.journey.JourneyApp.Profile.Models.ApplicationModel;
import com.example.journey.JourneyApp.Profile.Models.ProfileViewModel;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;

import retrofit2.http.Header;

public class AddApplicationModal extends BottomSheetDialogFragment {
    public static String TAG =  AddApplicationModal.class.toGenericString();

    Button cancelButton;
    Button addApplicationButton;
    EditText datePicker;
    EditText timePicker;
    TextInputLayout titleField;
    AutoCompleteTextView typeDropDown;
    ProfileViewModel profileViewModel;
    UserModel currentUserModel;
    String selectedType;
    String[] types = {"Visa", "HB1", "None"};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        currentUserModel = profileViewModel.getCurrentUserModel().getValue();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.add_application_modal, container, false);
        cancelButton = view.findViewById(R.id.cancel_button);
        addApplicationButton = view.findViewById(R.id.add_application);
        titleField = view.findViewById(R.id.title_input);
        typeDropDown = view.findViewById(R.id.type_edit_text);

        ArrayAdapter<String> dropdownInputAdapter = new ArrayAdapter<String>(getContext(), R.layout.dropdown_input_item, types);
        typeDropDown.setThreshold(3);
        typeDropDown.setAdapter(dropdownInputAdapter);
        typeDropDown.setText("Choose a type", false);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                                "Cancel button clicked", Toast.LENGTH_SHORT)
                        .show();
                dismiss();
            }
        });

        typeDropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedType = parent.getItemAtPosition(position).toString();
            }
        });

        addApplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String applicationName = String.valueOf(titleField.getEditText().getText());
                String date = String.valueOf(datePicker.getText());
                String time = String.valueOf(timePicker.getText());

                addNewApplication(applicationName, date, time);
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

        datePicker.setText(Helper.getLongDate());
        timePicker.setText(Helper.getShortTime());

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

    private void addNewApplication(String applicationName, String date, String time) {
        String dateCreated = date + ", " + time;

        // If we have a default application just rename it
        if (profileViewModel.applications.size() == 1) {
            ApplicationModel applicationModel = profileViewModel.applications.get(0);
            if (Objects.equals(applicationModel.getApplicationName(), "Default Application")) {
                applicationModel.setApplicationName(applicationName);
                applicationModel.setDateCreated(dateCreated);
                updateDefaultApplication(applicationModel);
                return;
            }
        }

        String key = profileViewModel.applicationsRef.push().getKey();
        ApplicationModel applicationModel = new ApplicationModel(UUID.randomUUID().toString(), applicationName, dateCreated);
        applicationModel.setPushKey(key);

        Database.DB_REFERENCE.child(Database.APPLICATIONS).child(currentUserModel.getUserID()).child(applicationModel.getPushKey()).setValue(applicationModel).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "DONE!");
                        } else {
                            Log.e(TAG, "FAILED TO ADD NEW APPLICATION FOR USER: " + currentUserModel.getUserID());
                        }
                    }
                }
        );
    }

    private void updateDefaultApplication(ApplicationModel applicationModel) {
        Database.DB_REFERENCE.child(Database.APPLICATIONS).child(currentUserModel.getUserID()).child(applicationModel.getPushKey()).setValue(applicationModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "SUCCESFULLY UPDATE DEFAULT APPLICATION");
                } else {
                    Log.e(TAG, "FAILED TO UPDATE DEFAULT APPLICATION");
                }
            }
        });
    }

    public void addSampleToDatabase() {
        Database.DB_REFERENCE.child(Database.APPLICATIONS).child(UUID.randomUUID().toString()).setValue("Something");
    }

    public void openDatePicker() {
        Calendar calendar = Calendar.getInstance();

        MaterialDatePicker _datePicker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        _datePicker.show(this.getChildFragmentManager(), _datePicker.toString());

        _datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                Long mili = (Long) selection;
                calendar.setTimeInMillis(mili);
                datePicker.setText(Helper.getDateFrom(calendar.getTime()));
            }
        });
    }

    public void openTimePicker() {
        LocalDateTime now = LocalDateTime.now();

        MaterialTimePicker _timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(now.getHour())
                .setMinute(now.getMinute())
                .setTitleText("Select Time")
                .build();
        _timePicker.show(this.getChildFragmentManager(), _timePicker.toString());

        _timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.setText(Helper.getTimeFrom(_timePicker.getHour(), _timePicker.getMinute()));
            }
        });
    }

    public void addApplication() {

    }
}
