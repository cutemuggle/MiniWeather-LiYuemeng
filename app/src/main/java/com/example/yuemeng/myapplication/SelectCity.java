package com.example.yuemeng.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.edu.pku.liyuemeng.app.MyApplication;
import cn.edu.pku.liyuemeng.bean.City;


public class SelectCity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBackBtn;
    private ListView listView = null;
    private TextView cityselected = null;
    private List<City> listcity = MyApplication.getInstance().getCityList();
    private int listSize = listcity.size();
    private String[] city = new String[listSize];
    // 在Select_City.java中声明String[] city数组，用于存放城市信息
    /*调用MyApplication的getInstance()方法取得实例，再调用该实例的getCityList（）的方法，返回mCityList
用listcity的长度建立string[]数组，用于存储要在ListView中展示的内容*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);

        mBackBtn = (ImageView) findViewById(R.id.title_back);
        cityselected = (TextView) findViewById(R.id.title_name);  //将选择的城市信息与顶部显示内容绑定
        mBackBtn.setOnClickListener(this);
        Log.i("city", listcity.get(1).getCity());
        for (int i = 0; i < listSize; i++){                      //用循环将listcity中的城市信息写入city数组
            city[i]=listcity.get(i).getCity();
            Log.d("City",city[i]);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice,city);    //simple_list_item_single_choice是android提供的带有单选框的布局，适配器中设置的布局指定的是列表每项内容的展示样式。
        listView = findViewById(R.id.list_view);           //绑定视图中的ListView
        listView.setAdapter(arrayAdapter);                 //设置适配器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> AdapterView, View view, int i, long l) {
                Toast.makeText(SelectCity.this,"have choosed:"+city[i],
                        Toast.LENGTH_SHORT).show();
                cityselected.setText("the current city:"+city[i]);     //列表项目是指监听器提示消息，并更改顶部栏显示城市。
            }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.title_back:
                int position =listView.getCheckedItemPosition();    //获取选中位置，并从listcity中找到对应的CityCode
                String select_cityCode = listcity.get(position).getNumber();
                Intent i = new Intent();
                i.putExtra("cityCode", "101160101");
                setResult(RESULT_OK, i);
                Log.d("cityCode",select_cityCode);   //利用intent将选中的citycode信息传回mainactivity
                finish();
                break;
            default:
                break;
        }
    }
}

