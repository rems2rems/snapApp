package fr.shiftit.snapapp;

import android.graphics.Bitmap;
import android.media.Image;

public class Snap {

    private Bitmap image;

    /**
     * pour serialisation/deserialisation vers/depuis l'api nodejs
     */
    private String imageData;
    private String description;

    private Location location;

    public Snap() {
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
