package com.coolweather.android.ui.notifications;

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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(getContext(), R.layout.notification_item, null);

                    TextView courseNameTextView = convertView.findViewById(R.id.courseName);
                    TextView examDateTextView = convertView.findViewById(R.id.examDate);

                    // ViewHolder是内部类，用于视图复用
                    ViewHolder viewHolder = new ViewHolder(courseNameTextView, examDateTextView);
                    convertView.setTag(viewHolder);
                }

                ViewHolder viewHolder = (ViewHolder) convertView.getTag();
                Entity entity = (Entity) getItem(position);  //调用适配器重写的方法
                viewHolder.getCourseNameTextView().setText(entity.getCourseName());  //设置控件值
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //Date格式转换为文本格式
//                viewHolder.getExamDateTextView().setText(sdf.format(entity.getExamDate()));  //设置控件值
                return convertView;
            }
        };
    }

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

        // 设置标题点击监听器，添加新实体并刷新列表
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //*  触发动画（ 1/2）：搞到全局 weatherId.
                //entityList.add(new Entity("Chemical", new Date(), "时间 9:00-11:00, 实验楼222"));
                //* 添加( 2/2)："Chemical", new Date(), "时间 9:00-11:00, 实验楼222"：  使用weatherId替代. (  全局被存储的；得调用动画)
                baseAdapter.notifyDataSetChanged();  // 通知适配器数据已更新，刷新列表     //* adapter默认：刷新列表.
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

    static class ViewHolder {
        TextView courseNameTextView;
        TextView examDateTextView;

        ViewHolder(TextView courseNameTextView, TextView examDateTextView) {
            this.courseNameTextView = courseNameTextView;
            this.examDateTextView = examDateTextView;
        }

        TextView getCourseNameTextView() {
            return courseNameTextView;
        }

        TextView getExamDateTextView() {
            return examDateTextView;
        }
    }
}
