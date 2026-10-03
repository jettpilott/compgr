import java.awt.*;
import java.util.Random;

public class Sky {
    private int width;
    private int height;
    private int[] starX;
    private int[] starY;
    private int[] starR;
    private int[] starPhase;

    private static final int count = 90;

    public Sky(final int width, final int height) {
        this.width = width;
        this.height = height;
        generateStars();
    }

    private void generateStars() {
        starX = new int[count];
        starY = new int[count];
        starR = new int[count];
        starPhase = new int[count];

        Random rnd = new Random(42);
        for (int i = 0; i < count; i++) {
            starX[i] = rnd.nextInt(Math.max(1, width));
            starY[i] = rnd.nextInt(Math.max(1, (int) (height * 0.55)));
            starR[i] = 1 + rnd.nextInt(2);
            starPhase[i] = rnd.nextInt(100);
        }
    }

    public void draw(Graphics2D g, final int width, final int height) {
        GradientPaint skyGradient = new GradientPaint(
                0, 0, new Color(8, 14, 22),
                0, (float) (height * 0.6), new Color(18, 32, 28)
        );
        g.setPaint(skyGradient);
        g.fillRect(0, 0, width, height);

        for (int i = 0; i < count; i++) {
            double twinkle = 0.6 + 0.4 * Math.sin((System.currentTimeMillis() / 900.0) + starPhase[i]);
            int alpha = (int) (150 * twinkle);
            g.setColor(new Color(220, 240, 255, Math.max(0, Math.min(255, alpha))));
            g.fillOval(starX[i], starY[i], starR[i], starR[i]);
        }

        drawMoon(g, width, height);
    }

    private void drawMoon(Graphics2D g, int width, int height) {
        int moonX = (int) (width * 0.78);
        int moonY = (int) (height * 0.14);
        int moonR = 46;

        for (int i = 8; i >= 1; i--) {
            g.setColor(new Color(200, 220, 200, 7));
            g.fillOval(moonX - moonR - i * 4, moonY - moonR - i * 4,
                    (moonR + i * 4) * 2, (moonR + i * 4) * 2);
        }

        g.setColor(new Color(232, 240, 226));
        g.fillOval(moonX - moonR, moonY - moonR, moonR * 2, moonR * 2);

        drawCrater(g, moonX - 22, moonY - 20, 13, 90);  // левый верх
        drawCrater(g, moonX + 18, moonY - 22, 10, 80);  // правый верх
        drawCrater(g, moonX - 24, moonY + 14,  9, 70);  // левый низ
        drawCrater(g, moonX + 16, moonY + 20, 12, 95);  // правый низ
        drawCrater(g, moonX +  2, moonY +  2,  8, 65);  // центр, самый мелкий
        drawCrater(g, moonX + 22, moonY -  2,  7, 55);  // правый бок

        g.setColor(new Color(180, 195, 180, 90));
        g.setStroke(new BasicStroke(1.2f));
        g.drawOval(moonX - moonR, moonY - moonR, moonR * 2, moonR * 2);
    }

    private void drawCrater(Graphics2D g, int cx, int cy, int d, int alpha) {
        int r = d / 2;

        g.setColor(new Color(170, 185, 170, alpha / 2));
        g.fillOval(cx - r - 2, cy - r - 2, d + 4, d + 4);

        g.setColor(new Color(160, 175, 160, alpha));
        g.fillOval(cx - r, cy - r, d, d);

        int ir = d / 4;
        g.setColor(new Color(140, 155, 140, alpha));
        g.fillOval(cx - ir, cy - ir, ir * 2, ir * 2);
    }
}