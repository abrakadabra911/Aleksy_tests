package Sokoban.view;

import Sokoban.controller.Controller;
import Sokoban.controller.EventListener;
import Sokoban.model.GameObjects;

import javax.swing.*;

public class View extends JFrame {
    private Controller controller;
    private Field field;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void init() {
        field = new Field(this);
        add(field);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(540, 580);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Sokoban");
        setVisible(true);
    }

    public void setEventListener(EventListener eventListener) {
        field.setEventListener(eventListener);
    }

    public void update() {
        field.repaint();
    }

    public GameObjects getGameObjects() {
        return controller.getGameObjects();
    }

    public void completed(int level) {
        int boxes = getGameObjects().getBoxes().size();
        update();
        JOptionPane.showMessageDialog(this,  " level + "+level+" is completed, good job!\n Vitaly zarobil " + boxes + " dolarow");
        controller.startNextLevel();
    }
}
