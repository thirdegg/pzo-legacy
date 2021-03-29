package util;

public class Overlap {
    public static boolean overlapCircles(Circle c1, Circle c2) {
        float distance = distSquared(c1, c2);
        float radiusSum = c1.r + c2.r;
        return distance <= radiusSum * radiusSum;
    }

    public static boolean pointInCircle(Circle c1, float x, float y) {
        return distSquared(c1, x, y) < c1.r * c1.r;
    }

    private static float distSquared(Circle c1, Circle other) {
        float distX = c1.x - other.x;
        float distY = c1.y - other.y;
        return distX * distX + distY * distY;
    }

    public static boolean pointPoint(float x, float y, float x1, float y1, int rsstoynie) {
        float distX = x - x1;
        float distY = y - y1;
        float tmp = distX * distX + distY * distY;
        return tmp < rsstoynie * rsstoynie;
    }

    private static float distSquared(Circle c1, float x, float y) {
        float distX = c1.x - x;
        float distY = c1.y - y;
        return distX * distX + distY * distY;
    }

    public static boolean overlapRectang(Rectang r1, Rectang r2) {
        return r1.x < r2.x + r2.width && r1.x + r1.width > r2.x && r1.y < r2.y + r2.height && r1.y + r1.height > r2.y;
    }

    public static boolean pointRectang(float x, float y, Rectang r) {
        return x > r.x && x < r.x + r.width && y > r.y && y < r.y + r.height;
    }
}
