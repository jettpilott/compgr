import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawPanel extends JPanel implements ActionListener {

    private final int TIMER_DELAY;
    private final Timer timer;
    private int ticksFromStart = 0;

    private Sky sky;
    private Forest forest;
    private Fog fog;
    private Campfire campfire;

    public DrawPanel(final int initialWidth, final int initialHeight, final int timerDelay) {
        this.TIMER_DELAY = timerDelay;

        this.setBackground(new Color(6, 12, 10));
        this.setPreferredSize(new Dimension(initialWidth, initialHeight));

        rebuild(initialWidth, initialHeight);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (campfire.contains(e.getX(), e.getY())) {
                    campfire.toggle();
                    repaint();
                }
            }
        });


        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (getWidth() > 0 && getHeight() > 0) {
                    rebuild(getWidth(), getHeight());
                }
            }
        });

        timer = new Timer(timerDelay, this);
        timer.start();
    }

    private void rebuild(final int width, final int height) {
        int fireX = (int) (width * 0.46);
        int fireY = (int) (height * 0.78);

        boolean wasBurning = (campfire == null) || campfire.isBurning();

        this.campfire = new Campfire(fireX, fireY);
        if (!wasBurning) {
            campfire.toggle();
        }

        this.forest = new Forest(width, height);
        this.fog = new Fog(width, height);
        this.sky = new Sky(width, height);
    }

    @Override
    protected void paintComponent(final Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        sky.draw(g, w, h);
        forest.drawFarLayer(g);
        forest.drawMidLayer(g);

        campfire.drawGlow(g, ticksFromStart);

        forest.drawNearLayer(g);
        campfire.draw(g, ticksFromStart);

        fog.draw(g, ticksFromStart);

    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == timer) {
            ++ticksFromStart;
            repaint();
        }
    }
}