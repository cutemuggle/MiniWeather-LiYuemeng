package cn.edu.pku.liyuemeng.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
public class LocationUtil {
    private LocationManager locationManager;
    private Context context;

    private LocationUtil(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * 用于获取当前所在地的城市名称
     *
     * @return cityName
     */
    public String getCurrentLocation() {
        // 有些用户的手机默认应用关闭定位权限，这条判断语句用于判断这种情形
        // 在用户没有开启定位权限的时候提示用户开启该权限
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "请允许赋予程序定位权限", Toast.LENGTH_LONG).show();
            return null;
        }

        // 使用网络提供商所提供的经纬度地址
        Location currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (currentLocation == null) {
            Toast.makeText(context, "无法获取当前位置", Toast.LENGTH_SHORT).show();
            return null;
        }

        // 根据经纬度来获取城市名称
        return getCityByLocation(currentLocation);
    }


    private String getCityByLocation(Location location) {
        // Android 自带的位置解析包，可以根据经纬度来解析地理位置
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses.size() > 0) {     // 如果找到了城市信息
                Address address = addresses.get(0);
                String city = address.getLocality();
                // 获取的城市名称带有 “市县乡州村”， 使用正则式除掉
                city = city.replaceAll("([市县乡州村])", "");
                Toast.makeText(context, String.format("当前位置 %s", city), Toast.LENGTH_SHORT).show();
                return city;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static LocationUtil getInstance(Context context) {
        return new LocationUtil(context);
    }

}
