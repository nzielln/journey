package com.example.journey.Sticker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.journey.R;

import org.w3c.dom.Text;

import java.util.List;

public class HistoryContactAdapter extends RecyclerView.Adapter<HistoryContactAdapter.ViewHolder>{


    private List<HistoryContact> historyList;
    private Context rcontext;


    public HistoryContactAdapter(Context context, List<HistoryContact> hList) {
        this.historyList = hList;
        this.rcontext = context;
    }



    @NonNull
    @Override
    public HistoryContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View historyView = inflater.inflate(R.layout.fragment_sticker_history,parent,false);
        HistoryContactAdapter.ViewHolder viewHolder = new ViewHolder(historyView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryContact cHistory = historyList.get(position);
        TextView emailAddressOfSender = holder.emailSenderTextView;
        TextView dateReceived = holder.dateTimeTextView;
        TextView imageReceived = holder.imageTextView;
        emailAddressOfSender.setText(cHistory.getSenderEmail());
        dateReceived.setText(cHistory.getDateTime());
        imageReceived.setText(cHistory.getImage());

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView emailSenderTextView;
            public TextView dateTimeTextView;
            public TextView imageTextView;

            public ViewHolder(View itemView){
                super(itemView);

                emailSenderTextView = (TextView) itemView.findViewById(R.id.history_email_text);
                dateTimeTextView =(TextView) itemView.findViewById(R.id.history_date_text);
                imageTextView = (TextView) itemView.findViewById(R.id.history_image_text);
            }
    }


}

