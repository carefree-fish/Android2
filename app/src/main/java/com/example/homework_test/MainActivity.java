package com.example.homework_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
//import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Fragment fragment1,fragment2,fragment3,fragment4;
    private FragmentManager manager;
    private LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4;
    private ImageView imageView1,imageView2,imageView3,imageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();   //隐藏顶栏项目名称
        setContentView(R.layout.activity_main);

        fragment1 = new Fragment1();  //初始化四个界面，实例化4个界面的对象
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        manager = getSupportFragmentManager();

        initial();
        select(1);    //初始界面为界面1

        linearLayout1.setOnClickListener(this);     //设置LinearLayout的监听
        linearLayout2.setOnClickListener(this);     //监听只能设在Oncreate中
        linearLayout3.setOnClickListener(this);     //此时已定义onclick，故可用this
        linearLayout4.setOnClickListener(this);

    }

    private void initial() {    //添加四个fragment
        linearLayout1 =findViewById(R.id.linearlayout1);   //底栏4个linerlayout
        linearLayout2 =findViewById(R.id.linearlayout2);
        linearLayout3 =findViewById(R.id.linearlayout3);
        linearLayout4 =findViewById(R.id.linearlayout4);
        imageView1=findViewById(R.id.imageView1);   //底栏4个imageview
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        imageView4=findViewById(R.id.imageView4);

        FragmentTransaction transaction=manager.beginTransaction();  //transaction:业务
        transaction.add(R.id.frag1,fragment1);  //fragment1、2、3、4传递给容器frag1，frag1包含4个视图
        transaction.add(R.id.frag1,fragment2);
        transaction.add(R.id.frag1,fragment3);
        transaction.add(R.id.frag1,fragment4);
        transaction.commit();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {           //view为所有控件的父类,从而能够接受所有的控件点击
        switch (v.getId()){
            case R.id.linearlayout1: select(1);
                break;
            case R.id.linearlayout2: select(2);
                break;
            case R.id.linearlayout3: select(3);
                break;
            case R.id.linearlayout4: select(4);
                break;
            default:
                break;
        }
    }

    private void select(int i) {    //选择展示界面,更改相应image
        hidden();
        switch(i){
            case 1:
                Log.d("right","界面1");
                showfragment(fragment1);
                darken();
                imageView1.setImageResource(R.drawable._12);//相应image变深色
                break;
            case 2:
                Log.d("right","界面2");
                showfragment(fragment2);
                darken();
                imageView2.setImageResource(R.drawable._22);
                break;
            case 3:
                Log.d("right","界面3");
                showfragment(fragment3);
                darken();
                imageView3.setImageResource(R.drawable._32);
                break;
            case 4:
                Log.d("right","界面4");
                showfragment(fragment4);
                darken();
                imageView4.setImageResource(R.drawable._42);
                break;
        }
    }

    private void darken(){      //其他image复原
        imageView1.setImageResource(R.drawable._11);
        imageView2.setImageResource(R.drawable._21);
        imageView3.setImageResource(R.drawable._31);
        imageView4.setImageResource(R.drawable._41);
    }

    private void hidden() {     //隐藏界面
        manager.beginTransaction()
                .hide(fragment1)
                .hide(fragment2)
                .hide(fragment3)
                .hide(fragment4)
                .commit();
    }
/**   或  ransaction=manager.beginTransaction()
                .hide()
                .hide()
                .hide()
                .hide();
        transaction.commit;
**/

    private void showfragment(Fragment fragment) {  //显式界面
        manager.beginTransaction()
                .show(fragment)
                .commit();
    }

}

//初始化，显式4个界面  没问题
//监听，只关联底栏，有关联动作      出错
//只设监听与判断，不设动作      出错
//将LinearLayout与TextView的初始化纳入initial后，正确
//未显示出recyclerView，监听似乎有效但logcat中报错
//设置监听动作select,select无动作    正确      监听似乎无效
//设置监听动作改变图片    正确，监听有效，但颜色为能复原，需修改
//修改后运行     良好
//增加监听动作显式界面，但应该界面没变化，观察能否正常运行      可
//增加动作隐藏    正常
//开放所以      界面1未隐藏，需调试
//改为select3 ，发现界面1似乎被设为背景板
//问题：1.界面1不消失   2.界面2不显示recyclerView  报错：E/RecyclerView: No adapter attached; skipping layout