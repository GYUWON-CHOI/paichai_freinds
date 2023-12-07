package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<ChatMessage> {

    private int layoutResource; // 각 채팅 메시지의 레이아웃 리소스 ID
    private List<ChatMessage> chatMessages; // 채팅 메시지 목록
    private FirebaseUser currentUser; // Firebase 사용자

    public ChatAdapter(Context context, int layoutResource, List<ChatMessage> chatMessages) {
        super(context, layoutResource, chatMessages);
        this.layoutResource = R.layout.list_item2;
        this.chatMessages = chatMessages;
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);
        }

        ChatMessage chatMessage = getItem(position);

        if (currentUser != null && chatMessage != null) {
            String currentUserEmail = currentUser.getEmail();
            Log.d("ChatAdapter", "Current User: " + currentUserEmail);

            TextView senderTextView = convertView.findViewById(R.id.senderTextView);
            TextView messageTextView = convertView.findViewById(R.id.messageTextView);

            // "@" 이전의 이메일 부분만 가져오기
            String[] emailParts = currentUserEmail.split("@");
            String currentUserDisplayName = emailParts[0];

            if (currentUserDisplayName.equalsIgnoreCase(chatMessage.getSender())) {
                // 현재 사용자가 보낸 메시지일 경우, 오른쪽에 정렬
                Log.d("ChatAdapter", "Right alignment");
                setAlignmentToRight(senderTextView, messageTextView);
            } else {
                // 다른 사용자가 보낸 메시지일 경우, 왼쪽에 정렬
                Log.d("ChatAdapter", "Left alignment");
                setAlignmentToLeft(senderTextView, messageTextView);
            }

            // 발신자와 메시지를 각각의 텍스트뷰에 표시
            senderTextView.setText(chatMessage.getSender());
            messageTextView.setText(chatMessage.getMessage());
        }

        return convertView;
    }

    private void setAlignmentToRight(TextView senderTextView, TextView messageTextView) {
        ViewGroup.LayoutParams params = senderTextView.getLayoutParams();
        if (params instanceof LinearLayout.LayoutParams) {
            // LinearLayout의 경우
            ((LinearLayout.LayoutParams) params).gravity = Gravity.END;
        } else if (params instanceof RelativeLayout.LayoutParams) {
            // RelativeLayout의 경우
            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_PARENT_END);
        }

        params = messageTextView.getLayoutParams();
        if (params instanceof LinearLayout.LayoutParams) {
            // LinearLayout의 경우
            ((LinearLayout.LayoutParams) params).gravity = Gravity.END;
        } else if (params instanceof RelativeLayout.LayoutParams) {
            // RelativeLayout의 경우
            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_PARENT_END);
        }
    }

    private void setAlignmentToLeft(TextView senderTextView, TextView messageTextView) {
        ViewGroup.LayoutParams params = senderTextView.getLayoutParams();
        if (params instanceof LinearLayout.LayoutParams) {
            // LinearLayout의 경우
            ((LinearLayout.LayoutParams) params).gravity = Gravity.START;
        } else if (params instanceof RelativeLayout.LayoutParams) {
            // RelativeLayout의 경우
            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_PARENT_START);
        }

        params = messageTextView.getLayoutParams();
        if (params instanceof LinearLayout.LayoutParams) {
            // LinearLayout의 경우
            ((LinearLayout.LayoutParams) params).gravity = Gravity.START;
        } else if (params instanceof RelativeLayout.LayoutParams) {
            // RelativeLayout의 경우
            ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.ALIGN_PARENT_START);
        }
    }
}

