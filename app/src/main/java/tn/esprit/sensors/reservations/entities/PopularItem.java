package tn.esprit.sensors.reservations.entities;

import java.util.List;

public class PopularItem {
    private String popularItemName;
    private int popularImageId;
    private String popularAdditionalData;

    private String popularLocation;

    private String popularPhone;

    private String populartime;

    private String temp;
    private String press;
    private String hum;



    public PopularItem(String popularItemName, int popularImageId, String popularAdditionalData, String popularLocation,String popularPhone,String populartime,String hum, String press, String temp) {
        this.popularItemName = popularItemName;
        this.popularImageId = popularImageId;
        this.popularAdditionalData = popularAdditionalData;
        this.popularPhone = popularPhone;
        this.populartime= populartime;
        this.popularLocation= popularLocation;
        this.hum=hum;
        this.press=press;
        this.temp=temp;

    }

    public String getPopularItemName() {
        return popularItemName;
    }

    public int getPopularImageId() {
        return popularImageId;
    }

    public String getPopularAdditionalData() {
        return popularAdditionalData;
    }

    public String getPopularPhone() {return popularPhone;}

    public String getPopularLocation (){ return popularLocation; }

    public String getPopulartime(){ return populartime;}
    public String getTemp() { return temp;}

    public String getPress() { return press;}

    public String getHum() { return hum;}
}
