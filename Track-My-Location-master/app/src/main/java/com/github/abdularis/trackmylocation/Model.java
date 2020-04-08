package com.github.abdularis.trackmylocation;

public class Model {
    int image;
    private boolean isSelected = false;
    String text;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Model(int image, String text) {
        this.image = image;
        this.text = text;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
