package tn.esprit.sensors.reservations.entities;

public class RecommendedItem {
    private String recommendedItemName;
    private int recommendedImageId;
    private String recommendedAdditionalData;

    private String recloc;

    private String recph;

    private String rect;

    private String temp;
    private String press;
    private String hum;




    public RecommendedItem(String recommendedItemName, int recommendedImageId, String recommendedAdditionalData,String recloc, String recph, String rect,String temp,String press,String hum) {
        this.recommendedItemName = recommendedItemName;
        this.recommendedImageId = recommendedImageId;
        this.recommendedAdditionalData = recommendedAdditionalData;
        this.recloc=recloc;
        this.rect=rect;
        this.recph=recph;
        this.temp=temp;
        this.hum=hum;
        this.press=press;
    }

    public String getRecommendedItemName() {
        return recommendedItemName;
    }

    public int getRecommendedImageId() {
        return recommendedImageId;
    }

    public String getRecommendedAdditionalData() {
        return recommendedAdditionalData;
    }

    public  String getRecloc(){ return recloc;}

    public  String getRect(){ return rect;}

    public  String getRecph(){ return  recph;}

    public String getTemp() { return temp;}

    public String getPress() { return press;}

    public String getHum() { return hum;}
}
