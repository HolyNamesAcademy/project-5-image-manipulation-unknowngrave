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
        this.red = truncate(red);
        this.green = truncate(green);
        this.blue = truncate(blue);
    }

    private int truncate(int value) {
        if (value < 0) {
            return 0;
        }

        if (value > 255) {
            return 255;
        }

        return value;
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
        this.red = truncate(red);
    }

    public void SetGreen(int green) {
        this.green = truncate(green);
    }

    public void SetBlue(int blue) {
        this.blue = truncate(blue);
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