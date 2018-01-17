package Sokoban.view;

import Sokoban.controller.EventListener;
import Sokoban.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

import static javax.swing.JOptionPane.YES_NO_OPTION;

public class Field extends JPanel {
    private View view;
    EventListener eventListener;

    public Field(View view) {
        this.view = view;
        KeyHandler keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void restartLevel() {
        int confirm = JOptionPane.showConfirmDialog(view, "restart level?", null, YES_NO_OPTION);
        if (confirm == 0) eventListener.restart();
    }

    @Override
    public void paint(Graphics g) {
        Set<GameObject> allGameObjects = view.getGameObjects().getAll();
        Set<Home> homes = view.getGameObjects().getHomes();
        Player player = view.getGameObjects().getPlayer();
        allGameObjects.remove(player);
        allGameObjects.removeAll(homes);
        //   g.setColor(new Color(230, 220, 200));

        BufferedImage background = null;
        String RESOURCE_PATH = getClass().getPackage().getName()
                .replaceAll("\\.", "/")
                .replace("Sokoban/view", "pic/Background.png");
        try {
            background = ImageIO.read(getClass().getClassLoader().getResource(RESOURCE_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //   g.fillRect(0, 0, 540, 580);
        g.drawImage(background, 0, 0, null);
        g.setColor(new Color(112, 146, 190));
        g.setFont(new Font("MyFont", Font.ITALIC, 12));
        // logo
        g.drawString("The game was created by Aliaksei Zayats", 280, 538);
        g.drawString("using Maven,MVC", 280, 550);
        g.drawString(" user: " + view.getUser(), 5, 538);
        g.drawString("level: " + view.getCurrentLevel(), 5, 550);
        for (GameObject object : allGameObjects) object.draw(g);
        for (Home home : homes) home.draw(g); // we need to have homes draw above boxes
        player.draw(g);// we need to have player draw above homes
    }


    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public class KeyHandler extends KeyAdapter {
        private boolean isAltPressed = false;
        private int buffer;

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case (KeyEvent.VK_LEFT):
                    eventListener.move(Direction.LEFT);
                    break;
                case (KeyEvent.VK_RIGHT):
                    eventListener.move(Direction.RIGHT);
                    break;
                case (KeyEvent.VK_UP):
                    eventListener.move(Direction.UP);
                    break;
                case (KeyEvent.VK_DOWN):
                    eventListener.move(Direction.DOWN);
                    break;
                case (KeyEvent.VK_R):
                    restartLevel();
                    break;
                case (KeyEvent.VK_ALT):
                    buffer = 0; //declared globally
                    isAltPressed = true;
            } //declared globally
        }

        @Override
        public void keyTyped(KeyEvent e) {
            if (isAltPressed)
                buffer = e.getKeyChar();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ALT) {
                isAltPressed = false;
                if (buffer == 100) {
                    int confirm = JOptionPane.showConfirmDialog(view, "Delete all users data?", null, YES_NO_OPTION);
                    if (confirm == 0) {
                        view.adminClearDB();
                        JOptionPane.showMessageDialog(view, "all data is deleted");
                    }
                }
            }
        }
    }
}

