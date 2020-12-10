package main.gui.mazefront.util;

import javafx.scene.image.ImageView;

public class Block {
    private final ImageView img;
    private final int row;
    private final int column;
    private final int value;


    public Block(ImageView img, int row, int column, int value) {
        this.img = img;
        this.row = row;
        this.column = column;
        this.value = value;
    }

    public ImageView getImg() {
        return img;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "value: " + this.value;
    }
}
