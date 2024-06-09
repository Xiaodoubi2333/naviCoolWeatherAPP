package com.coolweather.android.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    // 第三次修订：
//    setContentView(R.layout.fragment_home);         //* 自动启动 activity_main.xml，xml里面启动碎片fragment(  fragment后，略.)


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //* 我不喜欢的 textView
        /**/
        /**/
        /**/
        //        // 设置 TextView
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        // 确保 ChooseAreaFragment 正确加载（已经通过 fragment_home.xml 的 <fragment> 标签加载）
        // 无需在代码中再次处理


//阶段三：
        /*    调用WeatherActivity(  被碎片)：  ChatGPT提供      */
        /*    调用WeatherActivity(  被碎片)：  ChatGPT提供      */
        /*    调用WeatherActivity(  被碎片)：  ChatGPT提供      */
        // 检查 SharedPreferences 并启动 WeatherActivity
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (prefs.getString("weather", null) != null) {
            Intent intent = new Intent(getActivity(), WeatherActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        /*    调用WeatherActivity(  被碎片)：  ChatGPT提供      */
        /*    调用WeatherActivity(  被碎片)：  ChatGPT提供      */
        /*    调用WeatherActivity(  被碎片)：  ChatGPT提供      */
        return root;
    }
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            // Inflate the layout for this fragment
//            return inflater.inflate(R.layout.fragment_home, container, false);
//        }
}