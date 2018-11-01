package com.example.yuemeng.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.util.Log;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import cn.edu.pku.liyuemeng.app.MyApplication;
import cn.edu.pku.liyuemeng.bean.City;

/**
 * Created by liyuemeng on 22/10/2018.
 */


public class SelectCity extends AppCompatActivity implements View.OnClickListener {
    //08.1为选择城市界面的返回(ImageView)设置OnClick事件
    private ImageView mBackBtn;
    private ListView cityListLv;
    private List<City> mCityList;
    private MyApplication mApplication;
    private ArrayList<String> mArrayList;
    ArrayAdapter<String> adapter;
    //10.设置搜索功能
    private EditText searchEt;
    private ImageView searchBtn;
    private String selectNo;
    boolean searched = false;

    private String updateCityCode = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //08.1加载布局select_city
        setContentView(R.layout.select_city);
        //08.1为选择城市界面的返回(ImageView)设置OnClick事件
        mBackBtn = (ImageView) findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);
        //10 设置搜索功能
        searchEt = (EditText) findViewById(R.id.selectcity_search);
        searchBtn = (ImageView) findViewById(R.id.selectcity_search_button);
        searchBtn.setOnClickListener(this);

        //10 将ListView内容加载为我们从数据库文件读到的城市列表
        mApplication = (MyApplication) getApplication();
        mCityList = mApplication.getCityList();
        mArrayList = new ArrayList<String>();
        for (int i = 0; i < mCityList.size(); i++) {
            String cityName = mCityList.get(i).getCity();
            mArrayList.add(cityName);
        }
        cityListLv = (ListView) findViewById(R.id.selectcity_lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectCity.this, android.R.layout.simple_list_item_1, mArrayList);
        adapter.notifyDataSetChanged();
        cityListLv.setAdapter(adapter);

        final Intent intent = new Intent(this, MainActivity.class).setFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        //10添加ListView项的点击事件的动作
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (searched) {
                    updateCityCode = mCityList.get(Integer.parseInt(selectNo)).getNumber();
                } else {
                    updateCityCode = mCityList.get(position).getNumber();
                }
                Log.d("update city code", updateCityCode);

                //08.2在finish()之前，传递数据回MainActivity
                Intent i = new Intent();
                //10传递updateCityCode的数据进cityCode，MainActivity调用getStringExtra("text")获取该数据
                i.putExtra("cityCode", updateCityCode);
                Log.d("chuanshuchenggong", updateCityCode);
                //10传递i回RESULT_OK
                setResult(RESULT_OK, i);
                finish();

                //用Shareperference 存储最近一次的citycode
                SharedPreferences sharedPreferences = getSharedPreferences("CityCodePreference", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("citycode", updateCityCode);
                editor.commit();
            }
        };
        //10为组件绑定监听
        cityListLv.setOnItemClickListener(itemClickListener);
    }
/*
        //拼音识别
        searchEt.addTextChangedListener(new TextWatcher() {
            private Pattern inputFilter = Pattern.compile("([\\u4e00-\\u9fa5]|[a-z]|[A-Z])*");

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                newText = newText.replace(" ", "");

                if (newText.length() == 0) {
                    setAdapter(mCityList);
                    return;
                }

                List<City> target = new LinkedList<>();

                if (!inputFilter.matcher(newText).matches()) { // 如果输入包含汉字和字母意外的字符，则显示空
                    setAdapter(target);
                    return;
                }

                for (City entity : mCityList) {
                    if (matchName(newText, entity.getCity())) {
                        target.add(entity);
                    }
                }

                setAdapter(target);
            }

            private boolean matchName(String text, String cityName) {
                text = text.toLowerCase();
                if (cityName.contains(text)) {
                    return true;
                }

                int textPtr = -1, namePtr = -1;
                while (textPtr < text.length() - 1 && namePtr < cityName.length() - 1) {
                    textPtr++;
                    namePtr++;

                    if (text.charAt(textPtr) == cityName.charAt(namePtr)) {   // 如果汉字相同
                        continue;
                    }

                    String pinyin = Pinyin.toPinyin(cityName.charAt(namePtr)).toLowerCase();

                    if (text.startsWith(pinyin)) { // 如果包含全拼
                        textPtr += pinyin.length() - 1;
                        continue;
                    }

                    if ('a' <= text.charAt(textPtr) && text.charAt(textPtr) <= 'z') { // 如果包含拼音首字母
                        if (text.charAt(textPtr) == pinyin.charAt(0)) {
                            continue;
                        }
                    }

                    return false;
                }


                return true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void setAdapter(List<City> cityEntities) {
        ArrayAdapter adapter = new ArrayAdapter(SelectCity.this, android.R.layout.simple_list_item_1, cityEntities);
        cityListLv.setAdapter(adapter);
    }
*/
    //08.1为选择城市界面的返回(ImageView)设置OnClick事件  ---10.设置城市搜索功能并显示城市编号和名称
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectcity_search_button:
                String cityKey = searchEt.getText().toString();
                Log.d("Search", cityKey);
                mArrayList.clear();
                for (int i = 0; i < mCityList.size(); i++) {
                    String No_ = Integer.toString(i + 1);
                    String number = mCityList.get(i).getNumber();
                    String provinceName = mCityList.get(i).getProvince();
                    String cityName = mCityList.get(i).getCity();
                    if (number.equals(cityKey) || cityName.equals(cityKey)) {
                        searched = true;
                        selectNo = Integer.toString(i);
                        //mArrayList.clear();
                        mArrayList.add("NO." + No_ + ":" + number + "-" + provinceName + "-" + cityName);
                        Log.d("changed adapter data", "NO." + No_ + ":" + number + "-" + provinceName + "-" + cityName);
                    }

                    adapter = new ArrayAdapter<String>(SelectCity.this, android.R.layout.simple_list_item_1, mArrayList);
                    cityListLv.setAdapter(adapter);
                    //adapter.notifyDataSetChanged();
                }
                break;
            case R.id.title_back:
                finish();
                break;
            default:
                break;
        }
    }
}

