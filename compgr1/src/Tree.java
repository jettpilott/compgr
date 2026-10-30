import java.awt.*;


public class Tree {

    private final int baseX;
    private final int baseY;
    private final int height;
    private final int width;
    private final Color foliage;
    private final Color trunk;

    public Tree(final int baseX, final int baseY, final int height,
                final int width, final Color foliage, final Color trunk) {
        this.baseX = baseX;
        this.baseY = baseY;
        this.height = height;
        this.width = width;
        this.foliage = foliage;
        this.trunk = trunk;
    }

    public void draw(Graphics2D g) {
        drawTrunk(g);
        drawTiers(g);
    }

    private void drawTrunk(Graphics2D g) {
        int trunkW = Math.max(2, width / 10);
        int trunkH = height / 7;
        g.setColor(trunk);
        g.fillRect(baseX - trunkW / 2, baseY - trunkH, trunkW, trunkH);
        g.setColor(trunk.darker());
        g.drawLine(baseX - trunkW / 2, baseY - trunkH,
                baseX - trunkW / 2, baseY);
    }

    private void drawTiers(Graphics2D g) {
        int tiers = 5;
        int crownHeight = (int) (height * 0.88);
        int tierHeight = crownHeight / tiers;

        int topY = baseY - height;

        for (int i = 0; i < tiers; i++) {
            int tierTopY = topY + i * tierHeight;
            int tierBottomY = tierTopY + tierHeight + 3;

            double k = (i + 1) / (double) tiers;
            int tierW = (int) (width * (0.35 + 0.65 * k));

            int leftX = baseX - tierW / 2;
            int rightX = baseX + tierW / 2;

            g.setColor(dark(foliage, i));
            Polygon tier = new Polygon();
            tier.addPoint(baseX, tierTopY);
            tier.addPoint(leftX, tierBottomY);
            tier.addPoint(rightX, tierBottomY);
            g.fillPolygon(tier);
        }
    }

    private Color dark(Color c, int amount) {
        return new Color(
                Math.max(0, c.getRed() - amount * 4),
                Math.max(0, c.getGreen() - amount * 4),
                Math.max(0, c.getBlue() - amount * 4),
                c.getAlpha());
    }

}