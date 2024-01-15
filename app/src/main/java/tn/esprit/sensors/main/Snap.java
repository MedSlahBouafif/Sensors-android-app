package tn.esprit.sensors.main;

public class Snap {
    private String itemName;
    private int imageId;
    private String additionalData;

    public Snap(String itemName, int imageId, String additionalData) {
        this.itemName = itemName;
        this.imageId = imageId;
        this.additionalData = additionalData;
    }

    public String getItemName() {
        return itemName;
    }

    public int getImageId() {
        return imageId;
    }

    public String getAdditionalData() {
        return additionalData;
    }
}
