package main.gui.mazefront.actors;

import javafx.scene.image.ImageView;
import main.gui.mazefront.SpriteSheet;
import static main.gui.mazefront.util.Constants.*;

public class EntityFactory {

    private static SpriteSheet playerSheet = new SpriteSheet(new ImageView("main/gui/resources/spritesheet/battlesprites.png"));
    private static SpriteSheet stationaryEnemySpritesheet = new SpriteSheet((new ImageView("")));

    public static Entity generatePlayerAtPosition(int x, int y) {

        return null;
    }
}
