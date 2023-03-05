package com.example.journey.Sticker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.journey.R;
import com.example.journey.Sticker.Models.StickerUser;

import java.util.ArrayList;

public class StickerGridAdapter extends BaseAdapter {
    int[] stickers = {
            Constants.ANGRY,
            Constants.OK,
            Constants.LOL,
            Constants.LOVE,
            Constants.SAD,
            Constants.BORING,
            Constants.SHOCKED,
            Constants.TIRED
    };
    LayoutInflater layoutInflater;
    Context context;
    Boolean showCount = false;
    StickerUser user;

    public StickerGridAdapter(Context context, Boolean showCount) {
        this.context = context;
        layoutInflater = (LayoutInflater.from(context)) ;
        this.showCount = showCount;

    }
    public StickerGridAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater.from(context)) ;
    }

    public void updateUser(StickerUser user) {
        this.user = user;
        notifyDataSetChanged();
    }

    /*
    _____________
    Sender:
    Time:
    Sticker: []
    _____________
    Sender:
    Time:
    Sticker: []
    _____________
    Sender:
    Time:
    Sticker: []
    */
    @Override
    public int getCount() {
        return stickers.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.sticker_image_view, null); // inflate the layout
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.sticker_history_image);
        imageView.setImageDrawable(ContextCompat.getDrawable(context, stickers[position]));
        TextView count = (TextView) convertView.findViewById(R.id.count);

        if (!showCount) {
            count.setText(Constants.getStickerKey(stickers[position]).toUpperCase());
        } else {
            Integer stikerCount = user.getCountForSticker(stickers[position]);
            String plural = stikerCount > 1 ? "s" : "";
            count.setText(String.valueOf("SENT: " + stikerCount + " sticker" + plural ));
        }
        return convertView;
    }
}
