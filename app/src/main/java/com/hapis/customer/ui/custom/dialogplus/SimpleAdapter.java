package com.hapis.customer.ui.custom.dialogplus;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hapis.customer.R;

public class SimpleAdapter extends BaseAdapter {

  private LayoutInflater layoutInflater;
  private boolean isGrid;
  private String[] titleArray;
  private Integer[] iconsArray;
  private Drawable[] drawableArray;

  public SimpleAdapter(Context context, boolean isGrid, String[] titleArray, Integer[] iconsArray) {
    layoutInflater = LayoutInflater.from(context);
    this.isGrid = isGrid;
    this.titleArray = titleArray;
    this.iconsArray = iconsArray;
  }

  public SimpleAdapter(Context context, boolean isGrid, String[] titleArray, Drawable[] drawableArray) {
    layoutInflater = LayoutInflater.from(context);
    this.isGrid = isGrid;
    this.titleArray = titleArray;
    this.drawableArray = drawableArray;
  }

  @Override
  public int getCount() {
    return titleArray != null ? titleArray.length : 0;
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder;
    View view = convertView;

    if (view == null) {
      if (isGrid) {
        view = layoutInflater.inflate(R.layout.simple_grid_item, parent, false);
      } else {
        view = layoutInflater.inflate(R.layout.simple_list_item, parent, false);
      }

      viewHolder = new ViewHolder();
      viewHolder.textView = view.findViewById(R.id.text_view);
      viewHolder.imageView = view.findViewById(R.id.image_view);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }

    viewHolder.textView.setText(titleArray[position]);

    if(iconsArray != null && iconsArray.length > 0)
      viewHolder.imageView.setImageResource(iconsArray[position]);
    if(drawableArray != null && drawableArray.length > 0)
      viewHolder.imageView.setImageDrawable(drawableArray[position]);

    return view;
  }

  static class ViewHolder {
    TextView textView;
    ImageView imageView;
  }
}
