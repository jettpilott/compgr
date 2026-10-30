import java.awt.*;
import java.util.Random;

public class Fog {

    private static final int l_count = 7;

    private int width;
    private int height;
    private int[] lY;
    private int[] lHeight;
    private int[] layerOffset;
    private double[] layerSpeed;
    private int[] layerAlpha;

    public Fog(final int width, final int height) {
        this.width = width;
        this.height = height;
        generate();
    }

    private void generate() {
        lY = new int[l_count];
        lHeight = new int[l_count];
        layerOffset = new int[l_count];
        layerSpeed = new double[l_count];
        layerAlpha = new int[l_count];

        Random r = new Random(10);
        for (int i = 0; i < l_count; i++) {
            double t = i / (double) (l_count - 1);
            lY[i] = (int) (height * (0.55 + t * 0.35));
            lHeight[i] = 30 + r.nextInt(40);
            layerOffset[i] = r.nextInt(Math.max(1, width));
            layerSpeed[i] = 0.1 + r.nextDouble() * 0.83;
            layerAlpha[i] = 25 + r.nextInt(25);
        }
    }

    public void draw(Graphics2D g, int ticks) {
        for (int i = 0; i < l_count; i++) {
            int y = lY[i];
            int h = lHeight[i];
            int shift = (int) (layerOffset[i] + ticks * layerSpeed[i]) % (width + 400) - 200;

            g.setColor(new Color(180, 210, 200, layerAlpha[i]));
            for (int x = -300; x < width + 300; x += 200) {
                int xx = x + shift;
                g.fillOval(xx, y, 260, h);
                g.fillOval(xx + 90, y - 8, 180, h - 6);
            }
        }
    }
}