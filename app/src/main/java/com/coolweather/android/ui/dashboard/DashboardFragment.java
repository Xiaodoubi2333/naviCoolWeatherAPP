package com.coolweather.android.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.coolweather.android.R;
import com.coolweather.android.WeatherActivity;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

//        //* 文字
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        //
        // 获取SharedPreferences实例
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("GlobalPreferences", Context.MODE_PRIVATE);
        // 检索字符串，默认值为null
        String weatherId;
        weatherId = sharedPreferences.getString("weather_id", null);
        Intent intent = new Intent(getActivity(), WeatherActivity.class);           //* 罪魁祸首！home里的fragment启动的choose，一直都在
        intent.putExtra("weather_id", weatherId);
        startActivity(intent);

        return root;
    }
}