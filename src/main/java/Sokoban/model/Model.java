package Sokoban.model;

import Sokoban.controller.EventListener;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Model {
    public static Logger logger = Logger.getLogger(Model.class.getName()); // for logger use (not used)
    static FileHandler fh;                                                 // for logger use (not used)

    static {
        try {
            // This block configure the logger with handler and formatter // for logger use (not used)
            fh = new FileHandler("E:/MyJava/MyLogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Model(){ H2database = new ConnectH2();}

    public static final int FIELD_CELL_SIZE = 20; //dimention of game cell

    EventListener eventListener;

    private GameObjects gameObjects;

    private ConnectH2 H2database;

    private int currentLevel = 1;

    private String user = "";

    private String password = "";

    public String RESOURCE_PATH = getClass().getPackage().getName()
            .replaceAll("\\.", "/")
            .replace("Sokoban/model", "res/levels.txt");

    public URL getLevelsUrl() {
        URL url = getClass().getClassLoader().getResource(RESOURCE_PATH);
        return url;
   }

    private LevelLoader levelLoader = new LevelLoader(getLevelsUrl());

    public String getUser() {
        return user;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restartLevel(int level) {
        gameObjects = levelLoader.getLevel(level);
    }

    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        currentLevel++;
        updateUser(currentLevel+"");
        restartLevel(currentLevel);
    }

    public void move(Direction direction) {
        Player player = getGameObjects().getPlayer();
        if (checkWallCollision(player, direction)) return;
        if (checkBoxCollisionAndMoveIfAvaliable(direction)) return;
        player.move(direction);
        checkCompletion();
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        for (Wall wall : getGameObjects().getWalls()) {
            if (gameObject.isCollision(wall, direction)) return true;
        }
        return false;
    }

    public boolean checkBoxCollisionAndMoveIfAvaliable(Direction direction) {
        Player player = getGameObjects().getPlayer();
        for (Box box : getGameObjects().getBoxes()) {
            if (player.isCollision(box, direction) && (checkBoxCollision(box, direction) || checkWallCollision(box, direction)))
                return true;
            if (player.isCollision(box, direction) &&
                    !checkBoxCollision(box, direction) &&
                    !checkWallCollision(box, direction)) {
                box.move(direction);
                return false;
            }
        }
        return false;
    }

    public boolean checkBoxCollision(CollisionObject gameObject, Direction direction) {
        for (GameObject box : getGameObjects().getBoxes()) {
            if (gameObject.isCollision(box, direction)) return true;
        }
        return false;
    }

    public void checkCompletion() {
        boolean isBoxPresent;
        for (GameObject home : getGameObjects().getHomes()) {
            isBoxPresent = false;
            for (GameObject box : getGameObjects().getBoxes()) {
                if (box.getX() == home.getX() && box.getY() == home.getY()) {
                    isBoxPresent = true;
                    break;
                }
            }
            if (!isBoxPresent) return;
        }
        eventListener.levelCompleted(currentLevel);
    }

    public void adminClearDB() {
        H2database.adminClearDB();
    }

    public int getLastLevel(String user, String password) {
        int level = H2database.getLastLevel(user, password);
        if(level!=-1) {
            this.user = user;
            this.password = password;
            this.currentLevel = level;
            restartLevel(level);
        }
        else {
            JOptionPane.showMessageDialog(null, "invalid nickname or password");
        }
        return level;
    }

    public boolean createUser(String user, String password) {
        boolean created = H2database.createUser(user, password);
        if(created) {
            this.user = user;
            this.password = password;
            this.currentLevel = 1;
        }
        return created;
    }

    public void updateUser(String lastLevel) {
        H2database.updateUser(user, password, lastLevel);
    }
}
