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
import com.example.journey.JourneyApp.Profile.Adapters.DropdownArrayAdapter;
import com.example.journey.JourneyApp.Profile.Listeners.TimelineDelegate;
import com.example.journey.JourneyApp.Profile.Models.ApplicationModel;
import com.example.journey.JourneyApp.Profile.Models.ApplicationStatus;
import com.example.journey.JourneyApp.Profile.Models.ProfileViewModel;
import com.example.journey.JourneyApp.Profile.Models.TaskItemModel;
import com.example.journey.JourneyApp.Profile.Models.TimelineItemObject;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

public class UpdateApplicationModal extends BottomSheetDialogFragment {
    Button cancelButton;
    Button updatepplicationButton;
    EditText datePicker;
    EditText timePicker;
    TextInputLayout titleField;
    AutoCompleteTextView applicationsOptions;
    public static String TAG =  UpdateApplicationModal.class.toGenericString();
    String[] applications = {"Banana", "Apple", "Strawberry", "Kiwi"};
    AutoCompleteTextView statusDropdown;
    ProfileViewModel profileViewModel;
    UserModel currentUserModel;
    ApplicationModel currentApplication;
    TimelineDelegate delegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        delegate = bundle.getParcelable(Helper.TIMELINE_DELEGATE);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        currentUserModel = profileViewModel.getCurrentUserModel().getValue();
        currentApplication = profileViewModel.getCurrentApplication().getValue();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.update_application_modal, container, false);

        cancelButton = view.findViewById(R.id.cancel_button);
        updatepplicationButton = view.findViewById(R.id.update_application_button);
        applicationsOptions = view.findViewById(R.id.application_name_options);
        titleField = view.findViewById(R.id.title_input);

        DropdownArrayAdapter dropdownInputAdapter = new DropdownArrayAdapter(getContext(), R.layout.application_name_item, profileViewModel.applications);
        applicationsOptions.setThreshold(3);
        applicationsOptions.setAdapter(dropdownInputAdapter);
        applicationsOptions.setText(currentApplication.getApplicationName(), false);

        applicationsOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApplicationModel applicationModel = (ApplicationModel) parent.getItemAtPosition(position);
                profileViewModel.updateCurrentApplication(applicationModel);
            }
        });

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
                String title = String.valueOf(titleField.getEditText().getText());
                String date = String.valueOf(datePicker.getText());
                String time = String.valueOf(timePicker.getText());

                updateApplicationWithDetails(title, date, time);
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

    private void updateApplicationWithDetails(String title, String date, String time) {
        String dateTime = date + ", " + time;
        Integer rank = profileViewModel.timeline.size() + 1;
        DatabaseReference ref  = Database.DB_REFERENCE.child(Database.APPLICATIONS).child(currentUserModel.getUserID()).child(currentApplication.getPushKey()).child(Database.TIMELINE);
        String key = ref.push().getKey();
        TimelineItemObject timelineItemObject = new TimelineItemObject(title, currentApplication.getApplicationID(),UUID.randomUUID().toString(), dateTime,rank,false, key);

        ref.child(timelineItemObject.getPushKey()).setValue(timelineItemObject).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    task.getException().printStackTrace();
                } else {
                    Log.i(TAG, "UPDATED APPLICATION WITH TIMELINE OBJECT");
                    delegate.newItemAdded();
                }
            }
        });
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

    public void updateApplication() {

    }
}
