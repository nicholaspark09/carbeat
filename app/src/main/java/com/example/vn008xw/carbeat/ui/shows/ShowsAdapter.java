package com.example.vn008xw.carbeat.ui.shows;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.vn008xw.carbeat.data.vo.Show;
import com.example.vn008xw.carbeat.databinding.ListItemShowBinding;
import com.example.vn008xw.carbeat.utils.ImageUtil;
import com.example.vn008xw.carbeat.view.DataBoundAdapter;

public class ShowsAdapter extends DataBoundAdapter<Show, ListItemShowBinding> {

  private final ShowClickCallback mCallback;

  public ShowsAdapter(@NonNull ShowClickCallback showClickCallback) {
    mCallback = showClickCallback;
  }

  @Override
  protected ListItemShowBinding createBinding(ViewGroup parent) {
    ListItemShowBinding binding = ListItemShowBinding.inflate(LayoutInflater.from(parent.getContext()));
    binding.getRoot().setOnClickListener(v -> {
      final Show show = binding.getShow();
      if (show != null) mCallback.onClick(show);
    });
    return binding;
  }

  @Override
  protected void bind(ListItemShowBinding binding, Show item) {
    binding.setShow(item);
    if (ImageUtil.getLittleImage(item.getPosterPath()) != null)
      binding.setImageName(ImageUtil.getLittleImage(item.getPosterPath()));
  }

  @Override
  protected boolean areItemsTheSame(Show oldItem, Show newItem) {
    return oldItem.getId() == newItem.getId() && oldItem.equals(newItem);
  }

  @Override
  protected boolean areContentsTheSame(Show oldItem, Show newItem) {
    return oldItem.getName().equals(newItem.getName()) && oldItem.equals(newItem);
  }

  public interface ShowClickCallback {
    void onClick(@NonNull Show show);
  }
}
