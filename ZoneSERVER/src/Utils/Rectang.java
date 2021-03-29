package Utils;

public class Rectang {
    public float x, y, width, height, centw, centh;
    public boolean dom;

    public Rectang(float xx, float yy, float ww, float hh) {
        x = xx;
        y = yy;
        width = ww;
        height = hh;
        centw = width / 2;
        centh = height / 2;
    }
}
