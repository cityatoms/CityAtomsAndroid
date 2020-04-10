package com.foribus.cityatoms.adapter.models;

public class Model {

    int image;
    String text;
    private boolean isSelected = false;

    public Model(int image, String text) {
        this.image = image;
        this.text = text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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
