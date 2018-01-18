package Sokoban.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Player extends CollisionObject implements Movable {
    private static BufferedImage imageLeft;
    private static BufferedImage imageRight;
    private static BufferedImage imageUp;
    private static BufferedImage imageDown;

    private BufferedImage image = imageDown;

    private boolean rotatedToLeft = false;

    private String RESOURCE_PATH_LEFT = getClass().getPackage().getName()
            .replaceAll("\\.", "/")
            .replace("Sokoban/model", "pic/player_left.png");


    private String RESOURCE_PATH_RIGHT = getClass().getPackage().getName()
            .replaceAll("\\.", "/")
            .replace("Sokoban/model", "pic/player_right.png");

    private String RESOURCE_PATH_UP = getClass().getPackage().getName()
            .replaceAll("\\.", "/")
            .replace("Sokoban/model", "pic/player_up.png");

    private String RESOURCE_PATH_DOWN = getClass().getPackage().getName()
            .replaceAll("\\.", "/")
            .replace("Sokoban/model", "pic/player_down.png");

    public Player(int x, int y) {
        super(x, y);
        try {
            final URL url1 = getClass().getClassLoader().getResource(RESOURCE_PATH_LEFT);
            imageLeft = ImageIO.read(url1);
            final URL url2 = getClass().getClassLoader().getResource(RESOURCE_PATH_RIGHT);
            imageRight = ImageIO.read(url2);
            final URL url3 = getClass().getClassLoader().getResource(RESOURCE_PATH_UP);
            imageUp = ImageIO.read(url3);
            final URL url4 = getClass().getClassLoader().getResource(RESOURCE_PATH_DOWN);
            imageDown = ImageIO.read(url4);
          /*  imageLeft = ImageIO.read(new File(getClass().getClassLoader()   - working only within IDE
                    .getResource(RESOURCE_PATH_LEFT).toURI()));
            imageRight = ImageIO.read(new File(getClass().getClassLoader()    - working only within IDE
                    .getResource(RESOURCE_PATH_RIGHT).toURI()));*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics graphics) {
        int width = Model.FIELD_CELL_SIZE;
        graphics.drawImage(image, getX() - width / 2, getY() - width / 2, null);
    }


    @Override
    public void move(int x, int y) {
        setX(getX() + x);
        setY(getY() + y);
    }

    @Override
    public void move(Direction direction) {
        int x = 0, y = 0;
        if (direction == Direction.UP) {
            x = 0;
            y = -Model.FIELD_CELL_SIZE;
            image = imageUp;
        }
        if (direction == Direction.DOWN) {
            x = 0;
            y = +Model.FIELD_CELL_SIZE;
            image = imageDown;
        }
        if (direction == Direction.LEFT) {
            x = -Model.FIELD_CELL_SIZE;
            y = 0;
            image = imageLeft;
        }
        if (direction == Direction.RIGHT) {
            x = Model.FIELD_CELL_SIZE;
            y = 0;
            image = imageRight;
        }
        move(x, y);
    }
}
