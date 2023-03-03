package com.example.journey.Sticker;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AuthenticationViewModel extends ViewModel {
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<Boolean> showCreateAccount = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> shouldCreateNewAccount = new MutableLiveData<>();

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public MutableLiveData<Boolean> getShowCreateAccount() {
        return showCreateAccount;
    }

    public MutableLiveData<Boolean> getShouldCreateNewAccount() {
        return shouldCreateNewAccount;
    }

}
