package main.gui.mazefront;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
    private SpriteSheet spriteSheet;


//    public TileManager(Pane root) {
//        this.root = root;
//    }

    public TileManager(Pane root, SpriteSheet spriteSheet, String xmlPathToTileSheet) {
        this.root = root;
        this.spriteSheet = spriteSheet;
        this.path = xmlPathToTileSheet;
    }

    public void buildMap() {
//        try {
//            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newDefaultInstance();
//            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
//            Document document = builder.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
//
//            document.getDocumentElement().normalize();
//
//            NodeList nl = document.getElementsByTagName("tileset");
//            Node node = nl.item(0);
//            Element element = (Element) node;
//
//            blockWidth = Integer.parseInt(element.getAttribute("tilewidth"));
//            blockHeight = Integer.parseInt(element.getAttribute("tileheight"));
//            totalCount = Integer.parseInt(element.getAttribute("tilecount"));
//            columns = Integer.parseInt(element.getAttribute("columns"));
//
//            nl = document.getElementsByTagName("layer");
//            layers = nl.getLength();
//            data = new String[layers];
//
//            for (int layer = 0; layer < layers; ++layer) {
//                node = nl.item(layer);
//                element = (Element) node;
//                if (layer == 0) {
//                    width = Integer.parseInt(element.getAttribute("width"));
//                    height = Integer.parseInt(element.getAttribute("height"));
//                }
//
//                data[layer] = element.getElementsByTagName("data").item(0).getTextContent();
//                System.out.println("-----------------------------------------------------------------\n" + data[layer]);
//
//            }
//
//
//        } catch (Exception e) {
//            System.out.println("Exception Thrown!");
//        }

        init();
//
//        Pane level = new Pane();
//        ImageView temp = getImageByTileId(49);
//        temp.setX(0);
//        temp.setY(0);
//        ImageView temp2 = getImageByTileId(50);
//        temp2.setX(16);
//        temp2.setY(0);
//        level.getChildren().addAll(temp, temp2);
//        root.getChildren().add(level);

        for (String layer : data) {
            Pane pane = new Pane();
            setPane(pane, layer);
            root.getChildren().add(pane);
//            System.out.println(layer);
        }
//        Pane pane = new Pane();
//        setPane(pane, data[0]);
//        root.getChildren().add(pane);
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
//                System.out.println("-----------------------------------------------------------------\n" + data[layer]);

            }


        } catch (Exception e) {
            System.out.println("Exception Thrown!");
        }
    }

    public ImageView getImageByTileId(int id) {
        int row = id % 23;
        int column = (int) Math.ceil(id / 23.0);
//        int offsetX = blockWidth * (row - 1);
//        int offsetY = blockHeight * (column - 1);
//        Stream.of(row, column, offsetX, offsetY).forEach(System.out::println);
//        ImageView ss = spriteSheet.getImageAt(row, column);
        return spriteSheet.getImageAt(row, column);
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

                System.out.println("column: " + col);
                System.out.println("row: " + row);
                System.out.println("offsetX: " + offsetX);
                System.out.println("offsetY: " + offsetY);

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


}
