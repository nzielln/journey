package com.example.journey.Sticker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.R;

import java.util.List;

public class MessageHistoryAdapter extends RecyclerView.Adapter<MessageHistoryAdapter.ViewHolder>{

    private List<Message> historyList;
    private Context rcontext;

    public MessageHistoryAdapter(Context context, List<Message> hList) {
        this.historyList = hList;
        this.rcontext = context;
    }

    @NonNull
    @Override
    public MessageHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View historyView = inflater.inflate(R.layout.fragment_sticker_history,parent,false);
        MessageHistoryAdapter.ViewHolder viewHolder = new ViewHolder(historyView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message cHistory = historyList.get(position);
        TextView emailAddressOfSender = holder.emailSenderTextView;
        TextView dateReceived = holder.dateTimeTextView;
        ImageView imageReceived = holder.imageTextView;
        emailAddressOfSender.setText(cHistory.getSenderEmail());
        dateReceived.setText(cHistory.getDateTime());
        imageReceived.setImageDrawable(ContextCompat.getDrawable(rcontext, cHistory.getImage()));

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView emailSenderTextView;
            public TextView dateTimeTextView;
            public ImageView imageTextView;

            public ViewHolder(View itemView){
                super(itemView);

                emailSenderTextView = (TextView) itemView.findViewById(R.id.sticker_sender);
                dateTimeTextView =(TextView) itemView.findViewById(R.id.sticker_time);
                imageTextView = (ImageView) itemView.findViewById(R.id.sticker_content);
            }
    }

}

