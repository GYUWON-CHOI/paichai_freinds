package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.myapplication.R;

public class Frag3 extends Fragment {
    private View view;

    @Nullable
    @Override
    //하단 메뉴바
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3, container, false);

        return view;
    }
    //profile로 화면전환
    private void onProfileButtonClick(){
        Intent intent = new Intent(getActivity(),com.example.myapplication.profile.class);
        startActivity(intent);
    }
}
