package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ChatRoomAdapter extends ArrayAdapter<ChatRoom> {
    private int layoutResource;
    private List<ChatRoom> chatRooms;

    public ChatRoomAdapter(Context context, int layoutResource, List<ChatRoom> chatRooms) {
        super(context, layoutResource, chatRooms);
        this.layoutResource = R.layout.list_item_chat_room;
        this.chatRooms = chatRooms;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);
        }

        ChatRoom chatRoom = getItem(position);

        if (chatRoom != null) {
            TextView roomNameTextView = convertView.findViewById(R.id.roomNameTextView);
            roomNameTextView.setText(chatRoom.getRoomName());
        }

        return convertView;
    }

    @Override
    public ChatRoom getItem(int position) {
        return chatRooms.get(position);
    }
}
