package com.example.journey.Sticker.Models;

import com.example.journey.Sticker.Constants;
import com.example.journey.Sticker.HistoryContact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StickerUser {

    String email;

    ArrayList<HistoryContact> history;
    Map<String, Integer> stickers;

    public StickerUser(String email) {
        this.email = email;
        this.stickers = new HashMap<>();
    }

    public StickerUser() {
        this.stickers = new HashMap<>();
    }

    public void addSticker(int sticker) {

        stickers.compute(Constants.getStickerKey(sticker), (key, value) -> value == null ? 1: value + 1);
    }

    public Integer getCountForSticker(int sticker) {
        if (stickers.containsKey(Constants.getStickerKey(sticker))) {
            return stickers.get(Constants.getStickerKey(sticker));
        }

        return 0;
    }


    public String getEmail() {
        return email;
    }

    public Map<String, Integer> getStickers() {
        return stickers;
    }
}


