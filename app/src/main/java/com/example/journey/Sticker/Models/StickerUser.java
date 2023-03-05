package com.example.journey.Sticker.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.journey.Sticker.Constants;
import com.example.journey.Sticker.StickerMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StickerUser implements Parcelable {

    String email;
    String UUID;
    Map<String, Integer> stickers;
    ArrayList<StickerMessage> messages;

    public StickerUser(String email, String UUID) {
        this.email = email;
        this.UUID = UUID;
        this.stickers = new HashMap<>();
    }

    public StickerUser() {
        this.stickers = new HashMap<>();
    }

    protected StickerUser(Parcel in) {
        email = in.readString();
        UUID = in.readString();
    }

    public static final Creator<StickerUser> CREATOR = new Creator<StickerUser>() {
        @Override
        public StickerUser createFromParcel(Parcel in) {
            return new StickerUser(in);
        }

        @Override
        public StickerUser[] newArray(int size) {
            return new StickerUser[size];
        }
    };

    public void addSticker(int sticker) {

        stickers.compute(Constants.getStickerKey(sticker), (key, value) -> value == null ? 1: value + 1);
    }

    public Integer getCountForSticker(int sticker) {
        if (stickers.containsKey(Constants.getStickerKey(sticker))) {
            return stickers.get(Constants.getStickerKey(sticker));
        }

        return 0;
    }

    public String getUUID() {
        return UUID;
    }

    public String getEmail() {
        return email;
    }

    public void addNewMessage(StickerMessage newMessage) {
        messages.add(newMessage);
    }

    public ArrayList<StickerMessage> getMessages() {
        return messages;
    }

    public Map<String, Integer> getStickers() {
        return stickers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(UUID);
    }
}


