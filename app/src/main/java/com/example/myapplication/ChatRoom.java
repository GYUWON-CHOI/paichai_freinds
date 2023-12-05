package com.example.myapplication;

public class ChatRoom {
    private String roomId;
    private String roomName;

    public ChatRoom() {
        // Firebase에 필요한 기본 생성자
    }

    public ChatRoom(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }
}
