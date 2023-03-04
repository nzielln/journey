package com.example.journey.Sticker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.journey.R;
import com.example.journey.Sticker.Models.StickerUser;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseAdapter {
    ArrayList<StickerUser> users;
    Context context;
    LayoutInflater layoutInflater;

    public UserAdapter(Context context, ArrayList<StickerUser> users) {
        this.context = context;
        this.users = users;
        layoutInflater = (LayoutInflater.from(context));

    }

    public void addUsers(ArrayList<StickerUser> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return users.size();
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
            convertView = layoutInflater.inflate(R.layout.active_users, null); // inflate the layout
        }

        TextView textView = (TextView) convertView.findViewById(R.id.active_users_text);
        textView.setText(users.get(position).getEmail());

        return convertView;
    }
}
