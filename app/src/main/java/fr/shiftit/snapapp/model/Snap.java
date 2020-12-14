package fr.shiftit.snapapp.model;

import android.graphics.Bitmap;

import fr.shiftit.snapapp.util.ImageToText;

public class Snap {

    private transient String albumId;
    private transient String userId;

    private transient Bitmap image;

    /**
     * pour serialisation/deserialisation vers/depuis l'api nodejs
     */
    private String imageData;
    private String description;

    private Location location;

    public Snap() {
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        this.imageData = ImageToText.convertToString(image);
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
        this.image = ImageToText.convertToBitmap(imageData);
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
