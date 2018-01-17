package Sokoban.controller;

import Sokoban.model.ConnectH2;
import Sokoban.model.Direction;
import Sokoban.model.GameObjects;
import Sokoban.model.Model;
import Sokoban.view.View;

import javax.swing.*;
import java.util.logging.Level;

public class Controller implements EventListener {
    private View view;
    private Model model;
    private ConnectH2 H2database;

    public Controller() {
        H2database = new ConnectH2();
        this.view = new View(this);
        this.model = new Model();
        this.model.restart();
        this.view.init();
        model.setEventListener(this);
        view.setEventListener(this);
    }

    public static void main(String[] args) {
        try {
            Controller controller = new Controller();
        } catch (Exception e) {
            Model.logger.log(Level.WARNING, e + e.getStackTrace().toString() + " - some error");
        }
    }

    @Override
    public void move(Direction direction) {
        model.move(direction);
        view.update();
    }

    @Override
    public void restart() {
        model.restart();
        view.update();
    }

    @Override
    public void startNextLevel() {
        model.startNextLevel();
        view.update();
    }

    @Override
    public void levelCompleted(int level) {
        view.completed(level);
    }

    public GameObjects getGameObjects() {
        return model.getGameObjects();
    }

    public void adminClearDB() {
        H2database.adminClearDB();
    }

    public int getLastLevel(String nickname, String password) {
        int level = H2database.getLastLevel(nickname, password);
        if(level!=-1) model.startLastLevel(level);
        else {
            JOptionPane.showMessageDialog(view, "invalid nickname or password");
        }
       return level;
    }

    public boolean createUser(String user, String password) {
        return H2database.createUser(user, password);
    }

    public boolean updateUser(String lastLevel) {
        return
    }
}
