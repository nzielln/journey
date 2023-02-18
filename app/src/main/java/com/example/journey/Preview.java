package com.example.journey;

public class Preview {
    String url;
    String width;
    String height;

    public String getHeight() {
        return height;
    }

    public String getWidth() {
        return width;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Preview{" +
                "url='" + url + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
