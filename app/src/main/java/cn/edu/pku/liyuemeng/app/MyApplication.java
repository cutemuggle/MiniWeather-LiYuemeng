package cn.edu.pku.liyuemeng.app;

import android.app.Application;
import android.app.ListActivity;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.liyuemeng.bean.City;
import cn.edu.pku.liyuemeng.db.CityDB;

/**
 * Created by bq on 30/10/2018.
 */
//09建立MyApplication类
public class MyApplication extends Application {
    private static final String TAG ="MyAPP";
    //09创建getInstance方法
    private static MyApplication mApplication;
    //09打开数据库
    private CityDB mCityDB;
    //09初始化城市信息列表
    private List<City>mCityList;
    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG,"MyApplication->Oncreate");
        //09创建getInstance方法
        mApplication = this;
        //09打开数据库
        mCityDB=openCityDB();
        //09初始化城市信息列表
        initCityList();
    }
    //09初始化城市信息列表
    private void initCityList(){
        mCityList = new ArrayList<City>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                prepareCityList();
            }
        }).start();
    }
    //09初始化城市信息列表
    private boolean prepareCityList() {
        mCityList = mCityDB.getAllCity();
        int i=0;
        for (City city : mCityList) {
            i++;
            String cityName = city.getCity();
            String cityCode = city.getNumber();
            Log.d(TAG,cityCode+":"+cityName);
        }
        Log.d(TAG,"i="+i);
        return true;
    }
    //09初始化城市信息列表
    public List<City> getCityList() {
        return mCityList;
    }
    //09创建getInstance方法
    public static MyApplication getInstance(){
        return  mApplication;
    }

    //09创建打开数据库的方法
    private CityDB openCityDB() {
        String path = "/data"
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator + getPackageName()
                + File.separator + "databases1"
                + File.separator
                + CityDB.CITY_DB_NAME;
        File db = new File(path);
        Log.d(TAG,path);
        if (!db.exists()) {
            String pathfolder = "/data"
                    + Environment.getDataDirectory().getAbsolutePath()
                    + File.separator + getPackageName()
                    + File.separator + "databases1"
                    + File.separator;
            File dirFirstFolder = new File(pathfolder);
            if(!dirFirstFolder.exists()){
                dirFirstFolder.mkdirs();
                Log.i("MyApp","mkdirs");
            }
            Log.i("MyApp","db is not exists");
            try {
                InputStream is = getAssets().open("city.db");
                FileOutputStream fos = new FileOutputStream(db);
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    fos.flush();
                }
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return new CityDB(this, path);
    }
}
