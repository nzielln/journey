package com.example.journey;

public class Sticker {
        private String imageUrl;
        private String name;

        public Sticker() {
            // Default constructor required for calls to DataSnapshot.getValue(Sticker.class)
        }

        public Sticker(String imageUrl, String name) {
            this.imageUrl = imageUrl;
            this.name = name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getName() {
            return name;
        }
}
