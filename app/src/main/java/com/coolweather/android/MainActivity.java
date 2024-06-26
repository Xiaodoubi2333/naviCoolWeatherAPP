package com.coolweather.android;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.readmework,
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications

        ).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);


//      第4次修改：解决了homefragment报错：
        /*
        * 启动 MainActivity 时出现了空指针异常。
        * 根据日志显示，问题出现在 ActionBar.setTitle() 方法上，
        * 表明在 MainActivity 中尝试对 ActionBar 进行操作时发生了空指针异常。
        * */
/**/

//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        }
        NavigationUI.setupWithNavController(navView, navController);
    }

}
