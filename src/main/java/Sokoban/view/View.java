package Sokoban.view;

import Sokoban.controller.Controller;
import Sokoban.controller.EventListener;
import Sokoban.model.GameObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;

public class View extends JFrame {
    private Controller controller;
    private Field field;
    private int level;
    private String RESOURCE_PATH = getClass().getPackage().getName()  //  path to IconImage;
            .replaceAll("\\.", "/")
            .replace("Sokoban/view", "pic/icon.png");

    public View(Controller controller) {
        this.controller = controller;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //  setIconImage(image);
            final URL url = getClass().getClassLoader().getResource(RESOURCE_PATH);
            ImageIcon image = new ImageIcon(url);
            setIconImage(image.getImage());
        } catch (Exception e) {
        }
    }

    public void init() {
        loginDialog();
        field = new Field(this);
        add(field);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(540, 610);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Sokoban");
        initMenuBar();
        setVisible(true);
    }

    public String getUser() {
        return controller.getUser();
    }

    public int getCurrentLevel() {
        return controller.getCurrentLevel();
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
        JOptionPane.showMessageDialog(this, " level " + level + " is completed, good job!\n" +
                " Vitaly just moved " + boxes + " boxes");
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
            int result = JOptionPane.showOptionDialog(this, myPanel, "Sign in - Sokoban",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            switch (result) {
                case 0:
                    level = controller.getLastLevel(xField.getText(), yField.getText());
                    break;
                case 1:
                    if (newUserDialog()) level = 1;
                    break;
                default:
                    System.exit(0);
            }
        }
    }

    public boolean newUserDialog() {
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
                if (xField.getText().isEmpty() || yField.getText().isEmpty())
                    JOptionPane.showMessageDialog(this, "type user and password");
                else if (controller.createUser(xField.getText(), yField.getText())) {
                    JOptionPane.showMessageDialog(this, "successful");
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this, "user already exists!");
                    return false;
                }
                break;
            case 1:
                return false;
        }
        return false;
    }

    public void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        initHelpMenu(menuBar);
        getContentPane().add(menuBar, BorderLayout.NORTH);
    }

    public void initHelpMenu(JMenuBar menuBar) {
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        JMenuItem menuItemAbout = new JMenuItem(new AbstractAction("About") {
            public void actionPerformed(ActionEvent ae) {

                JOptionPane.showMessageDialog(null,
                        "This Sokoban-game was created by Aliaksei Zayats, using some java " +
                                "\ntechnologies and patterns like: Maven, MVC, embedded database H2 (for game progress)." +
                                "\nAll the source code you can find at: " +
                                "\n<html><a href=\"https://github.com/abrakadabra911/Aleksy_tests/tree/master/src/main/java/Sokoban\">https://github.com/abrakadabra911/Aleksy_tests/tree/master/src/main/java/Sokoban</a></html>" +
                                "\nemail: aliaksei.zayats@gmail.com", "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuItemAbout.setText("About");
        helpMenu.add(menuItemAbout);

        JMenuItem menuItemRules = new JMenuItem(new AbstractAction("Rules") {
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(null,
                        "2028 year.. Vitaly is just a regular everyday normal developer. " +
                                "\nHe just lost a job, now he is searching for a job in much more " +
                                "\nperspective scope - civil engineering. His first recruitment task is" +
                                "\nmoving all the wooden boxes to marked places." +
                                "\nAlt+D = delete user history(admin only)", "Rules", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuItemRules.setText("Rules");
        helpMenu.add(menuItemRules);

        JMenuItem menuItemRestart = new JMenuItem(new AbstractAction("Restart (R)") {
            public void actionPerformed(ActionEvent ae) {
                field.restartLevel();
            }
        });
        menuItemRestart.setText("Restart (R)");
        helpMenu.add(menuItemRestart);
    }

}
