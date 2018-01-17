package Sokoban.view;

import Sokoban.controller.Controller;
import Sokoban.controller.EventListener;
import Sokoban.model.GameObjects;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private Controller controller;
    private Field field;
    private int level;

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
        loginDialog();
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
        JOptionPane.showMessageDialog(this, " level + " + level + " is completed, good job!\n Vitaly zarobil " + boxes + " dolarow");
        controller.startNextLevel();
    }

    public void adminClearDB() {
        controller.adminClearDB();
    }

    public void loginDialog() {
        JTextField xField = new JTextField(10);
        JTextField yField = new JPasswordField(10);  // hidden characters

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("user:"));
        myPanel.add(xField);

        myPanel.add(new JLabel("password:"));
        myPanel.add(yField);

        level = -1;

        while (level == -1) {
            String[] options = {"login", "new User", "exit"};
            int result = JOptionPane.showOptionDialog(this, myPanel,"Sign in - Sokoban",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (result) {
                case 0:
                    level = controller.getLastLevel(xField.getText(), yField.getText()); break;
                case 1:
                    newUserDialog();
                    level = 1;
                    break;
                default:
                    System.exit(0);
            }
        }
    }

    public void newUserDialog() {
        JTextField xField = new JTextField(10);
        JTextField yField = new JPasswordField(10);  // hidden characters

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("user:"));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer

        myPanel.add(new JLabel("password:"));
        myPanel.add(yField);

        String[] options = {"create", "cancel"};
        int result = JOptionPane.showOptionDialog(this, myPanel, "Sign up - Sokoban",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        switch (result) {
            case 0:
                if(controller.createUser(xField.getText(), yField.getText()))
                    JOptionPane.showMessageDialog(this, "successful");
                else {JOptionPane.showMessageDialog(this, "user already exists!");}
                break;
            case 1:
                return;
        }
    }
}
