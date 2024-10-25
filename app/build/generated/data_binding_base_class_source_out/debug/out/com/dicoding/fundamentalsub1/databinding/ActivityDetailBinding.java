// Generated by view binder compiler. Do not edit!
package com.dicoding.fundamentalsub1.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.dicoding.fundamentalsub1.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDetailBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final Button btnOpenLink;

  @NonNull
  public final ImageView ivEventLogo;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final TextView tvEventDescription;

  @NonNull
  public final TextView tvEventName;

  @NonNull
  public final TextView tvEventOwner;

  @NonNull
  public final TextView tvEventQuota;

  @NonNull
  public final TextView tvEventTime;

  private ActivityDetailBinding(@NonNull ScrollView rootView, @NonNull Button btnOpenLink,
      @NonNull ImageView ivEventLogo, @NonNull ProgressBar progressBar,
      @NonNull TextView tvEventDescription, @NonNull TextView tvEventName,
      @NonNull TextView tvEventOwner, @NonNull TextView tvEventQuota,
      @NonNull TextView tvEventTime) {
    this.rootView = rootView;
    this.btnOpenLink = btnOpenLink;
    this.ivEventLogo = ivEventLogo;
    this.progressBar = progressBar;
    this.tvEventDescription = tvEventDescription;
    this.tvEventName = tvEventName;
    this.tvEventOwner = tvEventOwner;
    this.tvEventQuota = tvEventQuota;
    this.tvEventTime = tvEventTime;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_detail, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDetailBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnOpenLink;
      Button btnOpenLink = ViewBindings.findChildViewById(rootView, id);
      if (btnOpenLink == null) {
        break missingId;
      }

      id = R.id.ivEventLogo;
      ImageView ivEventLogo = ViewBindings.findChildViewById(rootView, id);
      if (ivEventLogo == null) {
        break missingId;
      }

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.tvEventDescription;
      TextView tvEventDescription = ViewBindings.findChildViewById(rootView, id);
      if (tvEventDescription == null) {
        break missingId;
      }

      id = R.id.tvEventName;
      TextView tvEventName = ViewBindings.findChildViewById(rootView, id);
      if (tvEventName == null) {
        break missingId;
      }

      id = R.id.tvEventOwner;
      TextView tvEventOwner = ViewBindings.findChildViewById(rootView, id);
      if (tvEventOwner == null) {
        break missingId;
      }

      id = R.id.tvEventQuota;
      TextView tvEventQuota = ViewBindings.findChildViewById(rootView, id);
      if (tvEventQuota == null) {
        break missingId;
      }

      id = R.id.tvEventTime;
      TextView tvEventTime = ViewBindings.findChildViewById(rootView, id);
      if (tvEventTime == null) {
        break missingId;
      }

      return new ActivityDetailBinding((ScrollView) rootView, btnOpenLink, ivEventLogo, progressBar,
          tvEventDescription, tvEventName, tvEventOwner, tvEventQuota, tvEventTime);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
