package com.example.journey.Sticker;


import android.media.Image;

import java.util.ArrayList;

/**
 * The AppUser class represents an
 * app user.
 */
public class AppUser {

  public String username;
  public Image image; // the sticker to be sent from one user another user
  public ArrayList<Image> imageList;

  public AppUser(String username, Image image, ArrayList<Image> imageList) {
    this.username = username;
    this.image = image;
    this.imageList = new ArrayList<Image>();
  }
}
