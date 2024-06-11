package com.coolweather.android.ui.readmework;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReadmeworkViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mDescription;

    public ReadmeworkViewModel() {
        mText = new MutableLiveData<>();
        mDescription = new MutableLiveData<>();

        mText.setValue("作品简介");
        mDescription.setValue("应用：SQLite，网络天气APP.\n" +
                "一、本应用使用底部导航，方便使用。\n\n" +
                "二、应用还涉及：网络JSON请求( 调用天气api)、天气信息显示、天气收藏夹\n" +
                "1.调用天气api：使用chooseAreaFragment，解析JSON；返回全局存储：countyName、weather_id\n" +
                "2.显示天气：全局存储SharedPreferences实例，调用，启动天气显示\n" +
                "3.天气收藏夹：使用EventBus库实现控件交互通信、内容提供者组件显示天气收藏夹.\n\n" +
                "三、综合：使用内容提供者组件、EventBus库进行组件间通信、ListView控件、BaseAdapter适配器实现收藏夹列表，" +
                "并使用SweetAlertDialog进行弹窗删除警告。");

    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getDescription() {
        return mDescription;
    }
}