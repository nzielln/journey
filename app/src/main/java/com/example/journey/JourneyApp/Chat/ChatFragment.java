package com.example.journey.JourneyApp.Chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.journey.R;

public class ChatFragment extends Fragment {
    private ListView chatFriendsList;
    private Button createNewChatButton;
    private EditText searchChat;
    private FrameLayout chatWindowContainer;
    private FrameLayout chatWindow;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate both layouts separately
        View mainView = inflater.inflate(R.layout.fragment_chat, container, false);
        View senderView = inflater.inflate(R.layout.fragment_chat_sender, container, false);
        View receiverView = inflater.inflate(R.layout.fragment_chat_receiver, container, false);
        View userView = inflater.inflate(R.layout.fragment_user_row, container, false);


        // Find views from the main layout
        chatFriendsList = mainView.findViewById(R.id.chat_friends_list);
        createNewChatButton = mainView.findViewById(R.id.create_new_chat_button);
        searchChat = mainView.findViewById(R.id.search_chat);
        chatWindowContainer = mainView.findViewById(R.id.chat_window_container);
        chatWindow = mainView.findViewById(R.id.chat_window);

        // Add the views from the users to the main layout
        ViewGroup userContainer = mainView.findViewById(R.id.user_container);
        userContainer.addView(userView);

        // Set up the other views here

        return mainView;
    }
}