package Sokoban.model;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;

public class Box extends CollisionObject implements Movable{
    private static BufferedImage image;
    private  String RESOURCE_PATH =  getClass().getPackage().getName()
            .replaceAll("\\.", "/")
            .replace("Sokoban/model", "pic/box2.png");
    public Box(int x, int y) {
        super(x, y);
        try {
            final URL url = getClass().getClassLoader().getResource(RESOURCE_PATH);
            image = ImageIO.read(url);
          //  image = ImageIO.read(new File(getClass().getClassLoader().getResource(RESOURCE_PATH).toURI())); - working only within IDE /
        }
        catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void draw(Graphics graphics) {
        int width = Model.FIELD_CELL_SIZE;
        graphics.drawImage(image, getX()-width/2, getY()-width/2,null);
    }

    @Override
    public void move(int x, int y) {
        setX(getX()+x);
        setY(getY()+y);
    }

    @Override
    public void move(Direction direction) {
        int x = 0, y = 0;
        if (direction == Direction.UP) {
            x=0; y=-Model.FIELD_CELL_SIZE;
        }
        if (direction == Direction.DOWN) {
            x=0; y=+Model.FIELD_CELL_SIZE;
        }
        if (direction == Direction.LEFT) {
            x=-Model.FIELD_CELL_SIZE; y=0;
        }
        if (direction == Direction.RIGHT) {
            x=Model.FIELD_CELL_SIZE; y=0;
        }
        move( x,  y);
    }
}
