package com.example.journey.Giphy;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.example.journey.R;

public class LoadingAlert {

  private ProgressBar loadingProgressCircle;

  public LoadingAlert(Context ct, View view) {
    loadingProgressCircle = view.findViewById(R.id.loadingCircle);
  }

  public void startLoading() {
    loadingProgressCircle.setVisibility(View.VISIBLE);
  }

  public void finishLoading() {
    loadingProgressCircle.setVisibility(View.GONE);
  }



}
