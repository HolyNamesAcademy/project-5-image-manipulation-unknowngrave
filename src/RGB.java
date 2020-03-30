/**
 * Created by hgotu on 3/11/2020.
 */
public class RGB {
    private int red;
    private int green;
    private int blue;

    public RGB() {
        red = 0;
        green = 0;
        blue = 0;
    }
    public RGB(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int GetRed() {
        return red;
    }

    public int GetGreen() {
        return green;
    }

    public int GetBlue() {
        return blue;
    }

    public void SetRed(int red) {
        this.red = red;
    }

    public void SetGreen(int green) {
        this.green = green;
    }

    public void SetBlue(int blue) {
        this.blue = blue;
    }
}
