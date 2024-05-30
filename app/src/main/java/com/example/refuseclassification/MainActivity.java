package com.example.refuseclassification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.refuseclassification.mainfragment.GuideFragment;
import com.example.refuseclassification.mainfragment.HomeFragment;
import com.example.refuseclassification.mainfragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ViewPager viewPager; // ViewPager用于展示多个Fragment的内容
    private BottomNavigationView navigation; // 底部导航栏
    private List<Fragment> fragmentList = new ArrayList<>(); // 存储Fragment的列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 设置布局文件
        initView(); // 初始化界面
    }

    // 初始化界面
    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager); // 获取ViewPager实例
        navigation = (BottomNavigationView) findViewById(R.id.nav_view); // 获取BottomNavigationView实例

        // 添加Fragment到列表中
        fragmentList.add(new HomeFragment());
        fragmentList.add(new GuideFragment());
        fragmentList.add(new SettingFragment());

        // 创建PagerAdapter，用于管理ViewPager中的Fragment
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), this, fragmentList);
        viewPager.setAdapter(adapter); // 设置ViewPager的适配器

        // 底部导航栏的选择监听器，用于处理导航栏点击事件
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 根据点击的菜单项切换ViewPager中的页面
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_guide:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_setting:
                        viewPager.setCurrentItem(2);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        // ViewPager的页面改变监听器，用于处理ViewPager滑动事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // 页面选中时，设置对应位置的导航栏菜单项为选中状态
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}

