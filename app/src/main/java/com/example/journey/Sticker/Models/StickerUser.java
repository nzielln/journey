package com.example.journey.Sticker.Models;

import java.util.HashMap;
import java.util.Map;

public class StickerUser {

    String email;
    Map<String, Integer> stickers;

    public StickerUser(String email) {
        this.email = email;
        this.stickers = new HashMap<>();
    }

    public void addSticker(String sticker) {
        stickers.compute(sticker, (key, value) -> value == null ? 1: value + 1);
    }

    public Integer getCountForSticker(String sticker) {
        if (stickers.containsKey(sticker)) {
            return stickers.get(sticker);
        }

        return 0;
    }


}


