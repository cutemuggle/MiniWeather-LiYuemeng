package cn.edu.pku.liyuemeng.bean;

public class City {
    private String province;
    private String city;
    private String number;
    private String firstPY;
    private String allPY;
    private String allFristPY;

    public City(String province, String city, String number, String firstPY, String allPY, String allFristPY) {
        this.province = province;
        this.city = city;
        this.number = number;
        this.firstPY = firstPY;
        this.allPY = allPY;
        this.allFristPY = allFristPY;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getNumber() {
        return number;
    }

    public String getFirstPY() {
        return firstPY;
    }

    public String getAllPY() {
        return allPY;
    }

    public String getAllFristPY() {
        return allFristPY;
    }

    public void setAllPY(String allPY) {
        this.allPY = allPY;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setFirstPY(String firstPY) {
        this.firstPY = firstPY;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAllFristPY(String allFristPY) {
        this.allFristPY = allFristPY;
    }

    public void setProvince(String province) {
        this.province = province;
    }


}
