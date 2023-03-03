package com.example.journey.Sticker;

import android.os.Parcelable;

public interface StickerAppDelegate {

    void signInUserWith(String email, String password);
    void createNewUserWith(String email, String password);

    void createNewAccountWasClicked();
    void alreadyHaveAnAccountWasClicked();

    void signInWasClicked();
    void createAccountWasClicked();

}
