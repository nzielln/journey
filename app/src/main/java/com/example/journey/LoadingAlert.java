package com.example.journey;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

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
