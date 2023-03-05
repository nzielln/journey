package com.example.journey.Sticker;

import android.graphics.drawable.Drawable;

import com.example.journey.R;

public class Constants {
    public static String DELEGATE = "DELEGATE";
    public static String RECIPIENT = "RECIPIENT";

    public static String ERROR_SIGNING_IN_MESSAGE = "There was an error signing you in. Please try again.";
    public static String ERROR_CREATING_ACCOUNT_MESSAGE = "There was an error creating your account. Please try again.";
    public static String USERS_DATABASE_ROOT = "users";
    public static String MESSAGES_DATABASE_ROOT = "messages";
    public static String ID_EMAIL_DATABASE_ROOT = "email_to_uuid";

    public final static String STICKER_ANGRY = "angry";
    public final static String STICKER_BORING = "boring";
    public final static String STICKER_TIRED = "tired";
    public final static String STICKER_SHOCKED = "shocked";
    public final static String STICKER_SAD = "sad";
    public final static String STICKER_OK = "ok";
    public final static String STICKER_LOL = "laugh";
    public final static String STICKER_LOVE = "love";

    public final static int ANGRY = R.drawable.angry;
    public final static int BORING = R.drawable.boring;
    public final static int TIRED = R.drawable.tired;
    public final static int SHOCKED = R.drawable.shocked;
    public final static int SAD = R.drawable.sad;
    public final static int OK = R.drawable.ok;
    public final static int LOL = R.drawable.laugh;
    public final static int LOVE = R.drawable.love;

    public static String getStickerKey(int sticker) {
        switch (sticker) {
            case SHOCKED: return STICKER_SHOCKED;
            case ANGRY: return STICKER_ANGRY;
            case SAD: return STICKER_SAD;
            case TIRED: return STICKER_TIRED;
            case BORING: return STICKER_BORING;
            case OK: return STICKER_OK;
            case LOL: return STICKER_LOL;
            case LOVE: return STICKER_LOVE;

        }
    return null;
    }

    public static Integer getStickerForPostion(int position) {
        switch(position) {
            case 0: return Constants.ANGRY;
            case 1: return Constants.OK;
            case 2: return  Constants.LOL;
            case 3: return  Constants.LOVE;
            case 4: return  Constants.SAD;
            case 5: return  Constants.BORING;
            case 6: return  Constants.SHOCKED;
            case 7: return  Constants.TIRED;
        }
        return null;

    }
    public static Integer getIDForStickerKey(String sticker) {
        switch (sticker) {
            case STICKER_SHOCKED: return SHOCKED;
            case STICKER_ANGRY: return ANGRY;
            case STICKER_SAD: return SAD;
            case STICKER_TIRED: return TIRED;
            case STICKER_BORING: return BORING;
            case STICKER_OK: return OK;
            case STICKER_LOL: return LOL;
            case STICKER_LOVE: return LOVE;

        }
        return null;
    }

    public static String formatEmailForPath(String email) {
        return email.replace(".", "_");

    }

}
