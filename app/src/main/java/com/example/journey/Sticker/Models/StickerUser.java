package com.example.journey.Sticker.Models;

import java.util.HashMap;
import java.util.Map;

public class StickerUser {

    String email;
    Map<Integer, Integer> stickers;

    public StickerUser(String email) {
        this.email = email;
        this.stickers = new HashMap<>();
    }

    public StickerUser() {
        this.stickers = new HashMap<>();
    }

    public void addSticker(int sticker) {
        stickers.compute(sticker, (key, value) -> value == null ? 1: value + 1);
    }

    public Integer getCountForSticker(int sticker) {
        if (stickers.containsKey(sticker)) {
            return stickers.get(sticker);
        }

        return 0;
    }

    public String getEmail() {
        return email;
    }

    public Map<Integer, Integer> getStickers() {
        return stickers;
    }
}


