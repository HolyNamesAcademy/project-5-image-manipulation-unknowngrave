/**
 * This class encapsulates a hue-saturation-lightness representation of a pixel.
 */
public class HSL {
    private int hue;
    private double saturation;
    private double lightness;

    // Constructors

    public HSL(int hue, double saturation, double lightness) {
        this.hue = hue;
        this.saturation = saturation;
        this.lightness = lightness;
    }

    // Getters

    public int GetHue() {
        return hue;
    }

    public double GetSaturation() {
        return saturation;
    }

    public double GetLightness() {
        return lightness;
    }

    /*
    Setters
    NOTE: these setters should only allow valid channel values.
    0 <= hue <= 360
    0 <= saturation <= 1
    0 <= lightness <= 1
     */
    public void SetHue(int hue) {
        if (hue > 360) {
            this.hue = 360;
            return;
        }

        if (hue < 0) {
            this.hue = 0;
            return;
        }

        this.hue = hue;
    }

    public void SetSaturation(double saturation) {
        if (saturation > 1.0) {
            this.saturation = 1.0;
            return;
        }
        if (saturation < 0.0) {
            this.saturation = 0.0;
            return;
        }

        this.saturation = saturation;
    }

    public void SetLightness(double lightness) {
        if (lightness > 1.0) {
            this.lightness = 1.0;
            return;
        }

        if (lightness < 0.0) {
            this.lightness = 0.0;
            return;
        }

        this.lightness = lightness;
    }

    /**
     * Converts an HSL representation of a pixel to an RGB representation. Here's some
     * info on how the algorithm works:
     *  https://en.wikipedia.org/wiki/HSL_and_HSV#To_RGB
     * @return an RGB representation of the pixel
     */
    public RGB GetRGB() {
        double chroma = (1 - Math.abs(2 * lightness - 1)) * saturation;
        double hprime = hue / 60.0;

        double x = chroma * (1 - Math.abs( hprime % 2  - 1));

        double r, g, b;
        switch ((int) Math.ceil(hprime)) {
            case 1: {
                r = chroma;
                g = x;
                b = 0;
                break;
            }
            case 2: {
                r = x;
                g = chroma;
                b = 0;
                break;
            }
            case 3: {
                r = 0;
                g = chroma;
                b = x;
                break;
            }
            case 4: {
                r = 0;
                g = x;
                b = chroma;
                break;
            }
            case 5: {
                r = x;
                g = 0;
                b = chroma;
                break;
            }
            case 6: {
                r = chroma;
                g = 0;
                b = x;
                break;
            }
            default: {
                r = 0;
                g = 0;
                b = 0;
                break;
            }
        }

        double m = lightness - chroma / 2;
        return new RGB((int) (255 * (r + m)), (int) (255 * (g + m)), (int) (255 * (b + m)));
    }
}