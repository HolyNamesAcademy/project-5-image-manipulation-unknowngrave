/**
 * This class encapsulates a red-green-blue representation of a pixel.
 */
public class RGB {
    private int red;
    private int green;
    private int blue;

    // Constructors

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

    // Getters

    public int GetRed() {
        return red;
    }

    public int GetGreen() {
        return green;
    }

    public int GetBlue() {
        return blue;
    }

    /*
    Setters
    NOTE: these setters should only allow valid channel values
    That means cases where a user tries to set channel value < 0
    or channel value > 255 should be handled properly.
     */
    public void SetRed(int red) {
        if (red > 255) {
            this.red = 255;
            return;
        }

        if (red < 0) {
            this.red = 0;
            return;
        }

        this.red = red;
    }

    public void SetGreen(int green) {
        if (green > 255) {
            this.green = 255;
            return;
        }

        if (green < 0) {
            this.green = 0;
            return;
        }

        this.green = green;
    }

    public void SetBlue(int blue) {
        if (blue > 255) {
            this.blue = 255;
            return;
        }

        if (blue < 0) {
            this.blue = 0;
            return;
        }

        this.blue = blue;
    }

    /**
     * Converts an RGB values to HSL values.
     * Here's some info on how the conversion works:
     *  https://math.stackexchange.com/questions/556341/rgb-to-hsv-color-conversion-algorithm
     * @return HSL representation of the pixel
     */
    public HSL ConvertToHSL() {
        double r = red / (double) 255;
        double g = green / (double) 255;
        double b = blue / (double) 255;

        double max = Math.max(r, Math.max(g, b));
        double min = Math.min(r, Math.min(g, b));

        double l = (max + min) / 2;
        double delta = max - min;

        double h = 0, s = 0;
        if (delta < 0.00001)
        {
            h = 0;
            s = 0;
        }
        else {
            if (max == r) {
                h = (g - b) / delta + (g < b ? 6 : 0);
            }
            else if (max == g) {
                h = (b - r) / delta + 2;
            }
            else if (max == b) {
                h = (r - g) / delta + 4;
            }
            h *= 60;

            s = l > 0.5 ? delta / (2 - max - min) : delta / (max + min);
        }

        return new HSL((int) h, s, l);
    }
}