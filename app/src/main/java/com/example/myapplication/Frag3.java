package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag3 extends Fragment {
    private View view;

    @Nullable
    @Override
    //하단 메뉴바
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3, container, false);
        TextView nicknameTextView = view.findViewById(R.id.nickname);
        return view;
    }
    //profile로 이동
    public void onProfileButtonClick(View view){
        Intent intent =new Intent(getActivity(),Profile.class);
        startActivity(intent);

    }
}
