package com.coolweather.android.ui.notifications;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.coolweather.android.ChooseAreaFragment;
import com.coolweather.android.R;
import com.coolweather.android.db.DatabaseHelper;
import com.coolweather.android.model.CountyModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private ListView listView;  //列表控件
    private List<CountyModel> entityList;  //实体列表
    private BaseAdapter baseAdapter; //适配器
    private View root;
    private DatabaseHelper databaseHelper;
    // 构造方法，初始化数据和适配器
    public NotificationsFragment() {
//        entityList = new ArrayList<>();  // 实例化实体列表
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
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.notification_item, parent,false);

                    TextView courseNameTextView = convertView.findViewById(R.id.courseName);
                    TextView examDetailsTextView = convertView.findViewById(R.id.examDetails);
                    ImageView imgDelete = convertView.findViewById(R.id.imgDelete);

                    // ViewHolder是内部类，用于视图复用
                    ViewHolder viewHolder = new ViewHolder(courseNameTextView, examDetailsTextView,imgDelete);
                    convertView.setTag(viewHolder);
                }

                ViewHolder viewHolder = (ViewHolder) convertView.getTag();
                CountyModel entity = (CountyModel) getItem(position);  //调用适配器重写的方法
                viewHolder.getCourseNameTextView().setText(entity.getCountyName());  //设置控件值examDetails
                viewHolder.getImgDelete().setImageDrawable(getActivity().getDrawable(R.mipmap.imgdelete));
                viewHolder.getImgDelete().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        delete(position);
                    }
                });
//                viewHolder.getExamDetailsTextView().setText(entity.getExamDetails());

                return convertView;
            }
        };
    }

    private void delete(final int position) {
//        确定  删除  是否删除？
//
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText("是否删除"+entityList.get(position).getCountyName()+"？");
        sweetAlertDialog.setCancelText("删除");
        sweetAlertDialog.setConfirmText("取消");
        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                sweetAlertDialog.setConfirmText("确定");
                sweetAlertDialog.setTitleText("删除成功!");
                sweetAlertDialog.showCancelButton(false);
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        int id = entityList.get(position).getId();
                        databaseHelper.deleteById(id);
                        entityList.remove(position);
                        baseAdapter.notifyDataSetChanged();
                    sweetAlertDialog.dismissWithAnimation();
                    }
                });
            }
        });
        sweetAlertDialog.show();

    }


//是对的
    //是对的
    //是对的

    // 创建视图
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        databaseHelper=new DatabaseHelper(getActivity());
        entityList = new ArrayList<>();  // 实例化实体列表
        // 获取 NotificationsFragment布局对应的视图
        root = inflater.inflate(R.layout.fragment_notifications, container, false);

        EventBus.getDefault().register(this);
        // 找列表视图、设置适配器、设置列表项单击监听
        listView = root.findViewById(R.id.listView);
        listView.setAdapter(baseAdapter);
        List<CountyModel> all = databaseHelper.getAll();
        entityList.addAll(all);
        baseAdapter.notifyDataSetChanged();

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // 单击列表项查看详情
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Entity entity = (Entity) listView.getItemAtPosition(position);          //* 项： 被点击 的 entity的项.
//            }
//        });


//是对的
        //是对的
        //是对的

    root.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            topDelete();
        }
    });
        // 设置标题点击监听器，添加新实体并刷新列表
        root.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
                //*  触发动画（ 1/2）：搞到全局 weatherId.
                //entityList.add(new Entity("Chemical", new Date(), "时间 9:00-11:00, 实验楼222"));
                // 1. 启动 ChooseAreaFragment
                Fragment chooseAreaFragment = new ChooseAreaFragment(1);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, chooseAreaFragment); //* 本地 fragment 被替换为 choose 的 XML
                transaction.addToBackStack(null); // 可选：添加到返回栈
                transaction.commit();

                // 2. 获取 SharedPreferences 实例
//                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("GlobalPreferences", Context.MODE_PRIVATE);
//                // 检索字符串，默认值为 null
//                String weatherId = sharedPreferences.getString("weather_id", null);
//                String selectCounty = sharedPreferences.getString("county_name", null);


//                // 创建 AlertDialog.Builder 实例
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("选中的县"); // 对话框标题
//                builder.setMessage(selectCounty); // 显示 selectCounty 存储的汉字
//
//                // 设置正面按钮
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // 点击确定按钮后的动作，这里什么也不做，只是关闭对话框
//                    }
//                });
//
//                // 创建并显示对话框
//                AlertDialog dialog = builder.create();
//                dialog.show();


                // 3. 得到 weatherId：从 "weather_id" (String) 出发，到一个 Weather
                //    entityList.add(new Entity("Chemical", new Date(), "时间 9:00-11:00, 实验楼222"));
//                entityList.add(new Entity(selectCounty, weatherId));            //* 报错原因： entityList的数据，不是<string,string>


//                entityList.add(new Entity("Chemical", "时间 9:00-11:00, 实验楼222"));


                // 通知适配器数据已更新，刷新列表（默认刷新列表）
                baseAdapter.notifyDataSetChanged();  //* adapter默认：刷新列表.
            }
        });

//        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        // 获取既可变又可观察的数据容器
//        LiveData<List<Entity>> mutableLiveEntityList = notificationsViewModel.getMutableLiveEntityList();
        // 观察（监听）数据变化，this 可以替换为 getViewLifecycleOwner() 。
        //mutableLiveEntityList.observe(this,new);
//        mutableLiveEntityList.observe(this, new Observer<List<Entity>>() {
//            @Override
//            public void onChanged(List<Entity> entityList) {
//                NotificationsFragment.this.entityList.addAll(entityList);
//                TextView getExamDetailsTextView;
////
////        ViewHolder(TextView courseNameTextView, TextView getExamDetailsTextView) {
////            this.courseNameTextView = courseNameTextView;
////            this.getExamDetailsTextView = getExamDetailsTextView;
////        }
////
////        TextView getCourseNameTextView() {
////            return courseNameTextView;
////        }
////
////        TextView getExamDetailsTextView() {
////            return getExamDetailsTextView;
//            }
//        });
        return root;
    }
    private String inputName="";
    private void topDelete() {
      AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
      builder.setTitle("title");
        final EditText editText = new EditText(getActivity());
        builder.setView(editText);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String s = editText.getText().toString();
                deleteName(s.trim());
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
           dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void deleteName(String name) {
        databaseHelper.deleteByName(name);
        List<CountyModel> all = databaseHelper.getAll();
        Log.e("list","list:"+all.toString());
        entityList.clear();
        entityList.addAll(all);
        baseAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEnvent(CountyModel countyModel) {
        root.findViewById(R.id.fragment_container).setVisibility(View.GONE);
        Log.e("countyModel", "countyModel：" + countyModel.toString());
//        Entity entity = new Entity(countyModel.getCountyName(), countyModel.getWeatherId());
        databaseHelper.insertCountyModel(countyModel.getCountyName(), countyModel.getWeatherId());

        List<CountyModel> all = databaseHelper.getAll();
        entityList.clear();
        entityList.addAll(all);
        baseAdapter.notifyDataSetChanged();
        for (int i = 0; i < all.size(); i++) {
            Log.e("myitem","item:"+all.get(i).toString());
        }
    }


}
