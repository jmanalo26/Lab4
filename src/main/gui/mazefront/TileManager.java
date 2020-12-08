package main.gui.mazefront;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.gui.mazefront.util.Block;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class TileManager {

    private int blockHeight = 16;
    private int blockWidth = 16;
    private int totalCount;
    private int columns;
    private int layers;
    private int width;
    private int height;
    private String[] data;

    private Pane root;
    private String path;
    private SpriteSheet tileSheet;


//    public TileManager(Pane root) {
//        this.root = root;
//    }

    public TileManager(Pane root, SpriteSheet spriteSheet, String xmlPathToTileSheet) {
        this.root = root;
        this.tileSheet = spriteSheet;
        this.path = xmlPathToTileSheet;
    }

    public TileManager(SpriteSheet tileSheet, String xmlPathToTileSheet){
        this.root = null;
        this.tileSheet = tileSheet;
        this.path = xmlPathToTileSheet;
    }


    public void buildMap() {
        init();

        for (String layer : data) {
            Pane pane = new Pane();
            setPane(pane, layer);
            root.getChildren().add(pane);
        }
    }

    private void init() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document document = builder.parse(new File(getClass().getClassLoader().getResource(path).toURI()));

            document.getDocumentElement().normalize();

            NodeList nl = document.getElementsByTagName("tileset");
            Node node = nl.item(0);
            Element element = (Element) node;

            blockWidth = Integer.parseInt(element.getAttribute("tilewidth"));
            blockHeight = Integer.parseInt(element.getAttribute("tileheight"));
            totalCount = Integer.parseInt(element.getAttribute("tilecount"));
            columns = Integer.parseInt(element.getAttribute("columns"));

            nl = document.getElementsByTagName("layer");
            layers = nl.getLength();
            data = new String[layers];

            for (int layer = 0; layer < layers; ++layer) {
                node = nl.item(layer);
                element = (Element) node;
                if (layer == 0) {
                    width = Integer.parseInt(element.getAttribute("width"));
                    height = Integer.parseInt(element.getAttribute("height"));
                }

                data[layer] = element.getElementsByTagName("data").item(0).getTextContent();
            }


        } catch (Exception e) {
            System.out.println("Exception Thrown!");
        }
    }

    public ImageView getImageByTileId(int id) {
        int row = id % columns;
        int column = (int) Math.ceil(id / (double) columns);
//        int offsetX = blockWidth * (row - 1);
//        int offsetY = blockHeight * (column - 1);
//        Stream.of(row, column, offsetX, offsetY).forEach(System.out::println);
//        ImageView ss = spriteSheet.getImageAt(row, column);
        return tileSheet.getImageAt(row, column);
    }

    private void setPane(Pane pane, String layerData) {

        String[] individualData = layerData.split(",");

        int counter = 0;
        for (String tile : individualData) {
            try {
                ImageView tempImg = getImageByTileId(Integer.parseInt(tile.trim()));
                int col = counter % width;
                int row = Math.floorDiv(counter, height);
                int offsetX = col * blockWidth;
                int offsetY = row * blockHeight;
//                System.out.println("Row: " + row);
//                System.out.println("Col: " + col);

                tempImg.setX(offsetX);
                tempImg.setY(offsetY);
                pane.getChildren().add(tempImg);
                counter++;
//                System.out.println(tile);
            } catch (RuntimeException e) {
                System.out.println("Exception thrown: " + tile);
                continue;
            }
        }
    }

    public Block[][] getBlocks() {
        init();
        Block[][] blockArray = new Block[width][height];
        for (int i = 0; i < data[0].length(); i++) {
            String[] individualData = data[0].split(",");
            int counter = 0;

            for (String tile : individualData) {
                try {
                    Integer blockId = Integer.parseInt(tile.trim());
                    ImageView tempImg = getImageByTileId(blockId);
                    int col = counter % width;
                    int row = Math.floorDiv(counter, height);
                    int offsetX = col * blockWidth;
                    int offsetY = row * blockHeight;

                    tempImg.setX(offsetX);
                    tempImg.setY(offsetY);

                    Block block;
                    if (blockId == 0 || blockId == 1) {
                        block = new Block(tempImg, row, col, 1);
                    } else {
                        block = new Block(tempImg, row, col, 0);
                    }
                    blockArray[row][col] = block;
                    counter++;

                } catch (RuntimeException e) {
                    System.out.println("Exception thrown: " + tile);
                    continue;
                }
            }
        }

        return blockArray;
    }

}
