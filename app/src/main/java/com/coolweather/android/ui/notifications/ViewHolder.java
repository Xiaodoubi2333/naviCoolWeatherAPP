package com.coolweather.android.ui.notifications;

import android.widget.TextView;

//
/*列表项对应的视图
 */
class ViewHolder {  //列表项对应的视图
    private TextView courseNameTextView;
    private TextView examDateTextView;
    //未包含实体类的第三个字段

    public ViewHolder(TextView tv_courseName, TextView tv_examDetails){  //构造方法
        this.courseNameTextView=tv_courseName;
        this.examDateTextView=tv_examDetails;
    }

    public TextView getCourseNameTextView(){   //getter
        return courseNameTextView;
    }

    public TextView getExamDateTextView(){   //getter
        return examDateTextView;
    }
}