import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Forest {

    private final List<Tree> farLayer = new ArrayList<>();
    private final List<Tree> midLayer = new ArrayList<>();
    private final List<Tree> nearLayer = new ArrayList<>();

    public Forest(final int width, final int height) {
        generate(width, height);
    }

    private void generate(final int width, final int height) {
        farLayer.clear();
        midLayer.clear();
        nearLayer.clear();

        Random rnd = new Random(7);
        int horizon = (int) (height * 0.72);

        int farCount  = Math.max(20, width / 40);
        int midCount  = Math.max(14, width / 60);

        for (int i = 0; i < farCount; i++) {
            int x = rnd.nextInt(width);
            int y = horizon + rnd.nextInt(20) - 10;
            int h = 55 + rnd.nextInt(35);
            int w = 18 + rnd.nextInt(10);
            farLayer.add(new Tree(x, y, h, w,
                    new Color(14, 30, 26), new Color(10, 18, 16)));
        }

        for (int i = 0; i < midCount; i++) {
            int x = rnd.nextInt(width);
            int y = horizon + 30 + rnd.nextInt(60);
            int h = 120 + rnd.nextInt(70);
            int w = 36 + rnd.nextInt(16);
            midLayer.add(new Tree(x, y, h, w,
                    new Color(9, 22, 18), new Color(6, 12, 10)));
        }

        addNearTree(width, rnd, 0.00, 0.15, height);
        addNearTree(width, rnd, 0.00, 0.15, height);
        addNearTree(width, rnd, 0.85, 1.00, height);
        addNearTree(width, rnd, 0.85, 1.00, height);
        addNearTree(width, rnd, 0.75, 0.88, height);
    }

    private void addNearTree(int width, Random rnd, double from, double to, int height) {
        int x = (int) (width * (from + rnd.nextDouble() * (to - from)));
        int y = height + 10;
        int h = 260 + rnd.nextInt(120);
        int w = 70 + rnd.nextInt(30);
        nearLayer.add(new Tree(x, y, h, w,
                new Color(4, 12, 10), new Color(3, 8, 7)));
    }

    public void drawFarLayer(Graphics2D g) {
        for (Tree t : farLayer) t.draw(g);
    }

    public void drawMidLayer(Graphics2D g) {
        for (Tree t : midLayer) t.draw(g);
    }

    public void drawNearLayer(Graphics2D g) {
        for (Tree t : nearLayer) t.draw(g);
    }
}