import java.awt.*;
import java.util.Random;

public class Campfire {

    private static final int GLOW_RADIUS = 130;

    private static final int CLICK_RADIUS = 40;

    private final int x;
    private final int y;
    private boolean burning = true;

    public Campfire(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isBurning() {
        return burning;
    }

    public void toggle() {
        burning = !burning;
    }

    public boolean contains(int px, int py) {
        int cx = x;
        int cy = y;
        int dx = px - cx;
        int dy = py - cy;
        return dx * dx + dy * dy <= CLICK_RADIUS * CLICK_RADIUS;
    }

    public void drawGlow(Graphics2D g, int ticks) {
        if (!burning) return;

        int maxRadius = GLOW_RADIUS;
        for (int r = maxRadius; r > 0; r -= 6) {
            double k = r / (double) maxRadius;
            int alpha = (int) ((1 - k) * (1 - k) * 50);
            g.setColor(new Color(255, 180, 90, Math.min(255, alpha)));
            g.fillOval(x - r, y - r, r * 2, r * 2);
        }
    }

    public void draw(Graphics2D g, int ticks) {
        g.setColor(new Color(20, 14, 10));
        g.fillOval(x - 26, y - 6, 52, 14);

        if (!burning) {
            g.setColor(new Color(50, 45, 45));
            g.fillOval(x - 10, y - 5, 14, 8);
            g.fillOval(x - 2, y - 6, 12, 8);
            g.setColor(new Color(70, 65, 65));
            g.fillOval(x - 6, y - 4, 6, 4);
            return;
        }

        g.setColor(new Color(255, 100, 30));
        g.fillOval(x - 18, y - 4, 36, 8);
        g.setColor(new Color(255, 200, 80));
        g.fillOval(x - 10, y - 3, 20, 5);

        Random rnd = new Random(ticks / 2);
        int flames = 5;
        for (int i = 0; i < flames; i++) {
            int dx = (i - flames / 2) * 5 + rnd.nextInt(3) - 1;
            int fh = 22 + rnd.nextInt(14);
            int fw = 10 + rnd.nextInt(6);
            int yy = y - 4 - fh;

            g.setColor(new Color(255, 90, 20, 200));
            g.fillOval(x + dx - fw / 2, yy, fw, fh);

            g.setColor(new Color(255, 170, 40, 220));
            g.fillOval(x + dx - fw / 3, yy + fh / 4, (int) (fw * 0.7), (int) (fh * 0.7));

            g.setColor(new Color(255, 240, 160, 230));
            g.fillOval(x + dx - fw / 4, yy + fh / 2, fw / 2, fh / 2);
        }

        for (int i = 0; i < 8; i++) {
            double phase = (ticks * 0.1 + i * 0.7) % 4.0;
            int sparkY = (int) (y - 10 - phase * 18);
            int sparkX = x + (int) (Math.sin(ticks * 0.05 + i) * 12);
            int alpha = (int) (255 * (1 - phase / 4.0));
            if (alpha < 0) alpha = 0;
            g.setColor(new Color(255, 200, 100, alpha));
            g.fillOval(sparkX, sparkY, 2, 2);
        }

        g.setColor(new Color(255, 130, 40, 180));
        g.setStroke(new BasicStroke(1.5f));
        g.drawArc(x - 14, y - 8, 28, 8, 0, 180);
    }
}