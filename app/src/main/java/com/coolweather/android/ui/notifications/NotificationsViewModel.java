package com.coolweather.android.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<List<Entity>> mutableLiveEntityList;

    public NotificationsViewModel() {
        mutableLiveEntityList = new MutableLiveData<>();
        // 初始化数据
        List<Entity> initialData = new ArrayList<>();
        // 添加一些初始数据，测试使用
        initialData.add(new Entity("Course 1", "Details 1"));
        initialData.add(new Entity("Course 2", "Details 2"));
        mutableLiveEntityList.setValue(initialData);
    }
    //  couresename和examdetails没实际意义，是我摘抄项目的时候代码就这么写的.  我实际用到：
    public LiveData<List<Entity>> getMutableLiveEntityList() {
        return mutableLiveEntityList;
    }
}
