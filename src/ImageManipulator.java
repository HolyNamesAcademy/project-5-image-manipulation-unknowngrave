import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Static utility class that is responsible for transforming the images.
 * Each function (or at least most functions) take in an Image and return
 * a transformed image.
 */
public class ImageManipulator {
    /**
     * Loads the image at the given path
     * @param path path to image to load
     * @return an Img object that has the given image loaded
     * @throws IOException
     */
    public static Img LoadImage(String path) throws IOException {
        return new Img(path);
    }

    /**
     * Saves the image to the given file location
     * @param image image to save
     * @param path location in file system to save the image
     * @throws IOException
     */
    public static void SaveImage(Img image, String path) throws IOException {
        String format = path.substring(path.lastIndexOf('.') + 1);
        image.Save(format, path);
    }

    /**
     * Converts the given image to grayscale (black, white, and gray). This is done
     * by finding the average of the RGB channel values of each pixel and setting
     * each channel to the average value.
     * @param image image to transform
     * @return the image transformed to grayscale
     */
    public static Img ConvertToGrayScale(Img image) {
        for (int i = 0; i < image.GetHeight(); i++) {
            for (int j = 0; j < image.GetWidth(); j++) {
                RGB rgb = image.GetRGB(j, i);
                int avg = (rgb.GetRed() + rgb.GetGreen() + rgb.GetBlue()) / 3;
                image.SetRGB(j, i, new RGB(avg, avg, avg));
            }
        }

        return image;
    }

    /**
     * Inverts the image. To invert the image, for each channel of each pixel, we get
     * its new value by subtracting its current value from 255. (r = 255 - r)
     * @param image image to transform
     * @return image transformed to inverted image
     */
    public static Img InvertImage(Img image) {
        for (int i = 0; i < image.GetHeight(); i++) {
            for (int j = 0; j < image.GetWidth(); j++) {
                RGB pixel = image.GetRGB(j, i);
                RGB invertedPixel = new RGB(255 - pixel.GetRed(), 255 - pixel.GetGreen(), 255 - pixel.GetBlue());
                image.SetRGB(j, i, invertedPixel);
            }
        }

        return image;
    }

    /**
     * Converts the image to sepia. To do so, for each pixel, we use the following equations
     * to get the new channel values:
     * r = .393r + .769g + .189b
     * g = .349r + .686g + .168b
     * b = 272r + .534g + .131b
     * @param image image to transform
     * @return image transformed to sepia
     */
    public static Img ConvertToSepia(Img image) {
        for (int i = 0; i < image.GetHeight(); i++) {
            for (int j = 0; j < image.GetWidth(); j++) {
                RGB rgb = image.GetRGB(j, i);

                int red = (int)(0.393 * rgb.GetRed() + 0.769 * rgb.GetGreen() + 0.189 * rgb.GetBlue());
                red = red > 255 ? 255 : red;

                int green = (int)(0.349 * rgb.GetRed() + 0.686 * rgb.GetGreen() + 0.168 * rgb.GetBlue());
                green = green > 255 ? 255 : green;

                int blue = (int)(0.272 * rgb.GetRed() + 0.534 * rgb.GetGreen() + 0.131 * rgb.GetBlue());
                blue = blue > 255 ? 255 : blue;

                image.SetRGB(j, i, new RGB(red, green, blue));
            }
        }

        return image;
    }

    private static double GetLuminance(RGB rgb) {
        return sqrt(0.299*pow(rgb.GetRed(), 2) + 0.587*pow(rgb.GetGreen(), 2) + 0.114*pow(rgb.GetBlue(), 2));
    }

    /**
     * Creates a stylized Black/White image (no gray) from the given image. To do so:
     * 1) calculate the luminance for each pixel. Luminance = (.299 r^2 + .587 g^2 + .114 b^2)^(1/2)
     * 2) find the median luminance
     * 3) each pixel that has luminance >= median_luminance will be white changed to white and each pixel
     *      that has luminance < median_luminance will be changed to black
     * @param image image to transform
     * @return black/white stylized form of image
     */
    public static Img ConvertToBW(Img image) {
        ArrayList<Double> arrayList = new ArrayList<>();
        for (int i = 0; i < image.GetHeight(); i++) {
            for (int j = 0; j < image.GetWidth(); j++) {
                RGB rgb = image.GetRGB(j, i);

                double luminance = GetLuminance(rgb);
                arrayList.add(luminance);
            }
        }

        arrayList.sort(Double::compareTo);
        double median = arrayList.get((arrayList.size() + 1) / 2);

        for (int i = 0; i < image.GetHeight(); i++) {
            for (int j = 0; j < image.GetWidth(); j++) {
                RGB rgb = image.GetRGB(j, i);

                double luminance = GetLuminance(rgb);
                if (luminance < median) {
                    image.SetRGB(j, i, new RGB(0, 0, 0));
                }
                else {
                    image.SetRGB(j, i, new RGB(255, 255, 255));
                }
            }
        }

        return image;
    }

    /**
     * Rotates the image 90 degrees clockwise.
     * @param image image to transform
     * @return image rotated 90 degrees clockwise
     */
    public  static Img RotateImage(Img image) {
        Img rotatedImage = new Img(image.GetHeight(), image.GetWidth());
        for (int i = 0; i < rotatedImage.GetHeight(); i++) {
            for (int j = 0; j < rotatedImage.GetWidth(); j++) {
                RGB oldPixel = image.GetRGB(i, image.GetHeight() - j - 1);
                rotatedImage.SetRGB(j, i, oldPixel);
            }
        }
        return rotatedImage;
    }

    /**
     * Applies an Instagram-like filter to the image. To do so, we apply the following transformations:
     * 1) We apply a "warm" filter. We can produce warm colors by reducing the amount of blue in the image
     *      and increasing the amount of red. For each pixel, apply the following transformation:
     *          r = r * 1.2
     *          g = g
     *          b = b / 1.5
     * 2) We add a vignette (a black gradient around the border) by combining our image with an
     *      an image of a halo (you can see the image at resources/halo.png). We take 65% of our
     *      image and 35% of the halo image. For example:
     *          r = .65 * r_image + .35 * r_halo
     * 3) We add decorative grain by combining our image with a decorative grain image
     *      (resources/decorative_grain.png). We will do this at a .95 / .5 ratio.
     * @param image image to transform
     * @return image with a filter
     * @throws IOException
     */
    public static Img InstagramFilter(Img image) throws IOException {
        // apply transformations
        for (int i = 0; i < image.GetHeight(); ++i) {
            for (int j = 0; j < image.GetWidth(); j++) {
                image.SetRGB(j, i, applyTransform(image.GetRGB(j, i)));
            }
        }

        // add halo overlay
        Img halo = new Img("resources/halo.png");
        for (int i = 0; i < image.GetHeight(); ++i) {
            for (int j = 0; j < image.GetWidth(); j++) {
                RGB haloPixel = halo.GetRGB(j * halo.GetWidth() / image.GetWidth(), i * halo.GetHeight() / image.GetHeight());
                RGB imagePixel = image.GetRGB(j, i);
                imagePixel.SetRed((int) (0.65 * imagePixel.GetRed() + 0.35 * haloPixel.GetRed()));
                imagePixel.SetGreen((int) (0.65 * imagePixel.GetGreen() + 0.35 * haloPixel.GetGreen()));
                imagePixel.SetBlue((int) (0.65 * imagePixel.GetBlue() + 0.35 * haloPixel.GetBlue()));

                image.SetRGB(j, i, imagePixel);
            }
        }

        // add decorative grain
        Img grain = new Img("resources/decorative_grain.png");
        for (int i = 0; i < image.GetHeight(); ++i) {
            for (int j = 0; j < image.GetWidth(); j++) {
                RGB grainPixel = grain.GetRGB(j * grain.GetWidth() / image.GetWidth(), i * grain.GetHeight() / image.GetHeight());
                RGB imagePixel = image.GetRGB(j, i);
                imagePixel.SetRed((int) (0.95 * imagePixel.GetRed() + 0.05 * grainPixel.GetRed()));
                imagePixel.SetGreen((int) (0.95 * imagePixel.GetGreen() + 0.05 * grainPixel.GetGreen()));
                imagePixel.SetBlue((int) (0.95 * imagePixel.GetBlue() + 0.05 * grainPixel.GetBlue()));

                image.SetRGB(j, i, imagePixel);
            }
        }
        return image;
    }

    public static RGB applyTransform(RGB rgb) {
        int r = (int) (rgb.GetRed() * 1.2);
        int g = rgb.GetGreen();
        int b = (int) (rgb.GetBlue() / 1.5);

        return new RGB(r, g, b);
    }

    /**
     * Sets the given hue to each pixel image. Hue can range from 0 to 360. We do this
     * by converting each RGB pixel to an HSL pixel, Setting the new hue, and then
     * converting each pixel back to an RGB pixel.
     * @param image image to transform
     * @param hue amount of hue to add
     * @return image with added hue
     */
    public static Img SetHue(Img image, int hue) {
        for (int i = 0; i < image.GetHeight(); ++i) {
            for (int j = 0; j < image.GetWidth(); j++) {
                HSL hsl = image.GetRGB(j, i).ConvertToHSL();
                hsl.SetHue(hue);
                image.SetRGB(j, i, hsl.GetRGB());
            }
        }

        return image;
    }

    /**
     * Sets the given saturation to the image. Saturation can range from 0 to 1. We do this
     * by converting each RGB pixel to an HSL pixel, setting the new saturation, and then
     * converting each pixel back to an RGB pixel.
     * @param image image to transform
     * @param saturation amount of saturation to add
     * @return image with added hue
     */
    public static Img SetSaturation(Img image, double saturation) {
        for (int i = 0; i < image.GetHeight(); ++i) {
            for (int j = 0; j < image.GetWidth(); j++) {
                HSL hsl = image.GetRGB(j, i).ConvertToHSL();
                hsl.SetSaturation(saturation);
                image.SetRGB(j, i, hsl.GetRGB());
            }
        }

        return image;
    }

    /**
     * Sets the lightness to the image. Lightness can range from 0 to 1. We do this
     * by converting each RGB pixel to an HSL pixel, setting the new lightness, and then
     * converting each pixel back to an RGB pixel.
     * @param image image to transform
     * @param lightness amount of hue to add
     * @return image with added hue
     */
    public static Img SetLightness(Img image, double lightness) {
        for (int i = 0; i < image.GetHeight(); ++i) {
            for (int j = 0; j < image.GetWidth(); j++) {
                HSL hsl = image.GetRGB(j, i).ConvertToHSL();
                hsl.SetLightness(lightness);
                image.SetRGB(j, i, hsl.GetRGB());
            }
        }

        return image;
    }
}
