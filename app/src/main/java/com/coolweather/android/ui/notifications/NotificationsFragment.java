package com.coolweather.android.ui.notifications;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.coolweather.android.ChooseAreaFragment;
import com.coolweather.android.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private ListView listView;  //列表控件
    private List<Entity> entityList;  //实体列表
    private BaseAdapter baseAdapter; //适配器
    private TextView textView;   //标题

    // 构造方法，初始化数据和适配器
    public NotificationsFragment() {
        entityList = new ArrayList<>();  // 实例化实体列表
        baseAdapter = new BaseAdapter() {  // 实例化适配器
            @Override
            public int getCount() {
                return entityList.size();
            }
            @Override
            public Object getItem(int position) {
                return entityList.get(position);
            }
            @Override
            public long getItemId(int position) {
                return position;
            }








            //**   步骤2：entityList.add(new Entity(selectCounty, weatherId));            //* 报错原因： entityList的数据，不是<string,string>
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(getContext(), R.layout.notification_item, null);

                    TextView courseNameTextView = convertView.findViewById(R.id.courseName);
                    TextView examDetailsTextView = convertView.findViewById(R.id.examDetails);

                    // ViewHolder是内部类，用于视图复用
                    ViewHolder viewHolder = new ViewHolder(courseNameTextView, examDetailsTextView);
                    convertView.setTag(viewHolder);
                }

                ViewHolder viewHolder = (ViewHolder) convertView.getTag();
                Entity entity = (Entity) getItem(position);  //调用适配器重写的方法
                viewHolder.getCourseNameTextView().setText(entity.getCourseName());  //设置控件值examDetails
                viewHolder.getExamDetailsTextView().setText(entity.getExamDetails());

                return convertView;
            }
        };
    }

















//是对的
    //是对的
    //是对的

    // 创建视图
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 获取 NotificationsFragment布局对应的视图
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        // 找列表视图、设置适配器、设置列表项单击监听
        textView = root.findViewById(R.id.textView);
        listView = root.findViewById(R.id.listView);
        listView.setAdapter(baseAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // 单击列表项查看详情
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Entity entity = (Entity) listView.getItemAtPosition(position);          //* 项： 被点击 的 entity的项.
            }
        });



//是对的
        //是对的
        //是对的











        // 设置标题点击监听器，添加新实体并刷新列表
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //*  触发动画（ 1/2）：搞到全局 weatherId.
                //entityList.add(new Entity("Chemical", new Date(), "时间 9:00-11:00, 实验楼222"));
                // 1. 启动 ChooseAreaFragment
                Fragment chooseAreaFragment = new ChooseAreaFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, chooseAreaFragment); //* 本地 fragment 被替换为 choose 的 XML
                transaction.addToBackStack(null); // 可选：添加到返回栈
                transaction.commit();

                // 2. 获取 SharedPreferences 实例
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("GlobalPreferences", Context.MODE_PRIVATE);
                // 检索字符串，默认值为 null
                String weatherId = sharedPreferences.getString("weather_id", null);
                String selectCounty = sharedPreferences.getString("county_name", null);





                // 创建 AlertDialog.Builder 实例
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("选中的县"); // 对话框标题
                builder.setMessage(selectCounty); // 显示 selectCounty 存储的汉字

                // 设置正面按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击确定按钮后的动作，这里什么也不做，只是关闭对话框
                    }
                });

                // 创建并显示对话框
                AlertDialog dialog = builder.create();
                dialog.show();








                // 3. 得到 weatherId：从 "weather_id" (String) 出发，到一个 Weather
                //    entityList.add(new Entity("Chemical", new Date(), "时间 9:00-11:00, 实验楼222"));
                entityList.add(new Entity(selectCounty, weatherId));            //* 报错原因： entityList的数据，不是<string,string>






//                entityList.add(new Entity("Chemical", "时间 9:00-11:00, 实验楼222"));







                // 通知适配器数据已更新，刷新列表（默认刷新列表）
                baseAdapter.notifyDataSetChanged();  //* adapter默认：刷新列表.
            }
        });

        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        // 获取既可变又可观察的数据容器
        LiveData<List<Entity>> mutableLiveEntityList = notificationsViewModel.getMutableLiveEntityList();
        // 观察（监听）数据变化，this 可以替换为 getViewLifecycleOwner() 。
        //mutableLiveEntityList.observe(this,new);
        mutableLiveEntityList.observe(this, new Observer<List<Entity>>() {
            @Override
            public void onChanged(List<Entity> entityList) {
                NotificationsFragment.this.entityList.addAll(entityList);
            }
        });

        return root;
    }

    //**   entityList.add(new Entity(selectCounty, weatherId));            //* 报错原因： entityList的数据，不是<string,string>
    static class ViewHolder {
        TextView courseNameTextView;
        TextView getExamDetailsTextView;

        ViewHolder(TextView courseNameTextView, TextView getExamDetailsTextView) {
            this.courseNameTextView = courseNameTextView;
            this.getExamDetailsTextView = getExamDetailsTextView;
        }

        TextView getCourseNameTextView() {
            return courseNameTextView;
        }

        TextView getExamDetailsTextView() {
            return getExamDetailsTextView;
        }
    }
}
