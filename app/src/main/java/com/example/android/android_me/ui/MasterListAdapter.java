package com.example.android.android_me.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

// Custom adapter that displays a list of Android-Me images in a GridView
public class MasterListAdapter extends BaseAdapter {

    //keeps track of the context and list of images to display
    private Context context;
    private List<Integer> imageIds;

    //Constructor
    public MasterListAdapter(Context context, List<Integer> imageIds) {
        this.context = context;
        this.imageIds = imageIds;
    }

    @Override
    public int getCount() {
        return imageIds.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    // Creates a new ImageView for each item referenced by the adapter
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView imageView;

        if (view == null){
            // If the view is not recycled, this creates a new ImageView to hold an image
            imageView = new ImageView(context);
            // Define the layout parameters
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        }else {
            imageView = (ImageView) view;
        }

        // Set the image resource and return the newly created ImageView
        imageView.setImageResource(imageIds.get(i));
        return imageView;
    }
}
