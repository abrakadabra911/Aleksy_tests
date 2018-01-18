package Sokoban.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Wall extends CollisionObject {
    private static BufferedImage image;
    private  String RESOURCE_PATH =  getClass().getPackage().getName()
            .replaceAll("\\.", "/")
            .replace("Sokoban/model", "pic/wall1.jpg");

    public Wall(int x, int y) {
        super(x, y);
        try {
            final URL url = getClass().getClassLoader().getResource(RESOURCE_PATH);
            image = ImageIO.read(url);
            //  image = ImageIO.read(new File(getClass().getClassLoader().getResource(RESOURCE_PATH).toURI())); - working only within IDE
        }
        catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void draw(Graphics graphics) {
      int width = Model.FIELD_CELL_SIZE;
        graphics.drawImage(image, getX()-width/2, getY()-width/2,null);
    }

}
