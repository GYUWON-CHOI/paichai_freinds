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

public class ChatAdapter extends ArrayAdapter<ChatMessage> {

    private int layoutResource; // 각 채팅 메시지의 레이아웃 리소스 ID
    private List<ChatMessage> chatMessages; // 채팅 메시지 목록

    public ChatAdapter(Context context, int layoutResource, List<ChatMessage> chatMessages) {
        super(context, layoutResource, chatMessages);
        this.layoutResource = R.layout.list_item2; // 변경된 부분
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);
        }

        ChatMessage chatMessage = getItem(position);

        if (chatMessage != null) {
            TextView senderTextView = convertView.findViewById(R.id.senderTextView);
            TextView messageTextView = convertView.findViewById(R.id.messageTextView);

            // 발신자와 메시지를 각각의 텍스트뷰에 표시
            senderTextView.setText(chatMessage.getSender());
            messageTextView.setText(chatMessage.getMessage());
        }

        return convertView;
    }


    @Override
    public ChatMessage getItem(int position) {
        return chatMessages.get(position);
    }
}
