import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Dimension;

import java.io.File;
import java.net.*;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



public class Board extends JPanel implements ActionListener{

    private final int ICRAFT_X = 282;
    private final int ICRAFT_Y = 550;
    private final int DELAY = 10;
    private Timer timer;
    private SpaceShip spaceShip;
    private Spiders spider;
    private List<Mushrooms> mushrooms;
    private boolean gflag = true;
    private List<Centipede> centipedes;
    private int score = 0;
    private int ccount = 0;
    private int spaceShiphealth = 3;




    public Board() {

        initBoard();
    }

    private void initBoard() {

        TAdapter lstn = new TAdapter();
        addMouseListener(lstn);
        addMouseMotionListener(lstn);
        setBackground(Color.BLACK);

        spaceShip = new SpaceShip(ICRAFT_X, ICRAFT_Y);
        spider = new Spiders(300, 350);

        int centipede_nos = 10;
        mushrooms = new ArrayList<>();

        //Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension d = new Dimension(500,450);
        Dimension dReal = d;
        dReal.setSize(d.width , d.height);
        for(int i = 0; i<20; i++) {
            int x = (int) Math.round(Math.random() * dReal.getWidth());
            int y = (int) Math.round(Math.random() * dReal.getHeight());
            mushrooms.add(new Mushrooms(x + 50 , y +50));
            System.out.println(x);
            System.out.println(y);
            System.out.println(" ");


        }

        centipedes = new ArrayList<>();
        int cx = 575;
        for (int i = 0; i < centipede_nos; i++) {
            centipedes.add(new Centipede(cx,0));
            cx -= 26;
        }

        timer = new Timer(DELAY, this);
        timer.start();


    }


    public void Scored(Graphics g)
    {
        String msg = "SCORE:" + score + "\nLIVES: " + spaceShiphealth;
        Font small = new Font("Helvetica", Font.BOLD, 15);
        FontMetrics fm = getFontMetrics(small);
        g.setColor(Color.BLUE);
        g.setFont(small);
        g.drawString(msg, 10, 20);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(gflag) {
            doDrawing(g);
            Scored(g);
        }
        else{
            drawGameOver(g);
        }


        Toolkit.getDefaultToolkit().sync();
    }
    private void drawGameOver(Graphics g) {
            String msg = "Game Over";
            Font small = new Font("Helvetica", Font.BOLD, 30);
            FontMetrics fm = getFontMetrics(small);

            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(msg, (600 - fm.stringWidth(msg)) / 2,
                    600 / 2);

    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        if (spaceShip.isVisible()) {
            g.drawImage(spaceShip.getImage(), spaceShip.getX(), spaceShip.getY(),
                    this);
        }
        if (spider.isVisible()) {
            g.drawImage(spider.getImage(), spider.getX(), spider.getY(),
                    this);
        }

        List<Missile> missiles = spaceShip.getMissiles();

        for (Missile missile : missiles) {

            g2d.drawImage(missile.getImage(), missile.getX(),
                    missile.getY(), this);
        }
        for (Mushrooms mushroom : mushrooms) {

            g2d.drawImage(mushroom.getImage(), mushroom.getX(),
                    mushroom.getY(), this);
        }
        for (Centipede centipede : centipedes) {

            g2d.drawImage(centipede.getImage(), centipede.getX(),
                    centipede.getY(), this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateMissiles();
        updateSpaceShip();
        updateMushrooms();
        updateCentipede();
        updateSpider();
        checkCollisions();
        repaint();

    }

    private void updateMissiles() {

        List<Missile> missiles = spaceShip.getMissiles();

        for (int i = 0; i < missiles.size(); i++) {

            Missile missile = missiles.get(i);

            if (missile.isVisible()) {
                missile.move();
            } else {

                missiles.remove(i);
            }
        }
    }
    private void updateCentipede() {

        for (int i = 0; i < centipedes.size(); i++) {

            Centipede centipede = centipedes.get(i);

            if (centipede.isVisible()) {
                centipede.move();
            } else {
                centipedes.remove(i);
            }
        }
    }

    private void updateSpaceShip() {
        if(spaceShip.isVisible()) {
            spaceShip.move();
        }
    }
    private void updateSpider() {
        if(spider.isVisible()) {
            spider.move();
        }
    }
    private void updateMushrooms() {

        for (int i = 0; i < mushrooms.size(); i++) {

            Mushrooms m = mushrooms.get(i);

            if (!m.isVisible()) {
                mushrooms.remove(i);
            }
        }
    }

    public void checkCollisions() {

        List<Missile> ms = spaceShip.getMissiles();
        Rectangle rss = spaceShip.getBounds();
        Rectangle spi = spider.getBounds();


        if(spider.isVisible()) {
            if(rss.intersects(spi)){
                spaceShiphealth -= 1;
                spaceShip.setVisible(false);

                for(Mushrooms m : mushrooms) {
                    if(m.isVisible()){
                        m.health = 3;
                    }
                }

                if(spaceShiphealth == 0) {
                    gflag = false;
                }
                if(gflag) {
                    spaceShip = new SpaceShip(ICRAFT_X, ICRAFT_Y);
                    spider = new Spiders(300,350);
                    centipedes = new ArrayList<>();
                    int cx = 575;
                    for (int i = 0; i < 10; i++) {
                        centipedes.add(new Centipede(cx,0));
                        cx -= 26;
                    }
                }
            }
        }
        //If Centipede touches ship
        for(Centipede c: centipedes){
            Rectangle r3 = c.getBounds();
            if(rss.intersects(r3)){
                spaceShiphealth -= 1;
                spaceShip.setVisible(false);
                for(Mushrooms m : mushrooms) {
                    if(m.isVisible()){
                        m.health = 3;
                    }
                }

                if(spaceShiphealth == 0) {
                    gflag = false;
                }
                if(gflag) {
                    spaceShip = new SpaceShip(ICRAFT_X, ICRAFT_Y);
                    spider = new Spiders(300,350);
                    centipedes = new ArrayList<>();
                    int cx = 575;
                    for (int i = 0; i < 10; i++) {
                        centipedes.add(new Centipede(cx,0));
                        cx -= 26;
                    }
                }
            }
        }
        //If Centipede touches Missile
        for (Missile m : ms) {
            Rectangle r1 = m.getBounds();
            for (Centipede c: centipedes){
                Rectangle r3 = c.getBounds();
                if(r1.intersects(r3)){
                    score += 2;
                    m.setVisible(false);
                    c.health -= 1;
                    if(c.health == 0) {
                        score += 5;
                        c.setVisible(false);
                        ccount += 1;
                        if(ccount == 10) {
                            score += 600;
                            ccount = 0;
                            centipedes = new ArrayList<>();
                            int cx = 575;
                            for (int i = 0; i < 10; i++) {
                                centipedes.add(new Centipede(cx,0));
                                cx -= 26;
                            }
                        }
                    }
                }
            }
        }
        //Centipede and Mushroom
        for(Mushrooms m : mushrooms){
            Rectangle r4 = m.getBounds();
            for(Centipede c : centipedes){
                Rectangle r3 = c.getBounds();
                if(r3.intersects(r4)) {
                    c.moveDown();
                }
            }
        }

        //Mushroom and missile touch
        for (Missile m : ms) {

            Rectangle r1 = m.getBounds();

            if(spider.isVisible()) {
                if (r1.intersects(spi)) {
                    spider.health -= 1;
                    score += 100;
                    m.setVisible(false);
                    if (spider.health == 0) {
                        spider.setVisible(false);
                        score += 600;
                    }
                }
            }

            for (Mushrooms mushroom : mushrooms) {

                Rectangle r2 = mushroom.getBounds();

                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    score += 1;
                    mushroom.health -= 1;
                    if(mushroom.health == 0) {
                        score += 5;
                        mushroom.setVisible(false);
                    }
                }
            }
        }


    }


    private class TAdapter extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            spaceShip.mouseMoved(e);

        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            spaceShip.mousePressed(e);
            Sounds sound = new Sounds();
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
            spaceShip.mouseEntered(e);
        }


        @Override
        public void mouseExited(MouseEvent e)
        {
            spaceShip.mouseExited(e);
        }

    }
}