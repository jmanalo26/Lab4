package main.gui.mazefront;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public class SpriteSheet {
    private ImageView spritesheet;
    private int row;
    private int column;
    //    private int offsetX;
//    private int offsetY;
    private int height;
    private int width;

    public SpriteSheet(ImageView spritesheet) {
        this.spritesheet = spritesheet;
    }

    public SpriteSheet(ImageView spritesheet, int width, int height) {
        this.spritesheet = spritesheet;
        this.height = height;
        this.width = width;
    }

    public ImageView getImageAt(int row, int column) {
        int offsetX = width * (row - 1);
        int offsetY = height * (column - 1);
        ImageView temp = new ImageView(spritesheet.getImage());
        temp.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
        return temp;
    }
//
//    public Image getImageAt(int tileId) {
//        spritesheet.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
//        return null;
//    }


}
