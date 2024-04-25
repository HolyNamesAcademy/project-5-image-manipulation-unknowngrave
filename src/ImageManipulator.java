import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
        Img pic = new Img(path);
        return pic;
    }

    /**
     * Saves the image to the given file location
     * @param image image to save
     * @param path location in file system to save the image
     * @throws IOException
     */
    public static void SaveImage(Img image, String path) throws IOException {
        image.Save("png", path);
    }

    /**
     * Converts the given image to grayscale (black, white, and gray). This is done
     * by finding the average of the RGB channel values of each pixel and setting
     * each channel to the average value.
     * @param image image to transform
     * @return the image transformed to grayscale
     */
    public static Img ConvertToGrayScale(Img image) {
        for(int i = 0; i < image.GetWidth(); i++){
            for(int j = 0; j < image.GetHeight(); j++){
                RGB value = image.GetRGB(i, j);
                int avg = ((value.GetRed() + value.GetBlue() + value.GetGreen()) / 3);
                value.SetRed(avg);
                value.SetBlue(avg);
                value.SetGreen(avg);
                image.SetRGB(i, j, value);
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
        for(int i = 0; i < image.GetWidth(); i++){
            for(int j = 0; j < image.GetHeight(); j++){
                RGB value = image.GetRGB(i, j);
                value.SetRed(255 - value.GetRed());
                value.SetBlue(255 - value.GetBlue());
                value.SetGreen(255 - value.GetGreen());
                image.SetRGB(i, j, value);
            }
        }
        return image;
    }

    /**
     * Converts the image to sepia. To do so, for each pixel, we use the following equations
     * to get the new channel values:
     * r = .393r + .769g + .189b
     * g = .349r + .686g + .168b
     * b = .272r + .534g + .131b
     * @param image image to transform
     * @return image transformed to sepia
     */
    public static Img ConvertToSepia(Img image) {
        for(int i = 0; i < image.GetWidth(); i++){
            for(int j = 0; j < image.GetHeight(); j++){
                RGB value = image.GetRGB(i, j);
                double red = (int)(0.393 * value.GetRed()) + (0.769 * value.GetGreen()) + (0.189 * value.GetBlue());
                double green = (int)(0.349 * value.GetRed()) + (0.686 * value.GetGreen()) + (0.168 * value.GetBlue());
                double blue = (int)(0.272 * value.GetRed()) + (0.534 * value.GetGreen()) + (0.131 * value.GetBlue());
                value.SetRed((int)red);
                value.SetBlue((int)blue);
                value.SetGreen((int)green);
                image.SetRGB(i, j, value);
            }
        }
        return image;
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
        RGB black = new RGB(0, 0, 0);
        RGB white = new RGB(255, 255, 255);
        ArrayList <Double> list = new ArrayList<>();

        for(int i = 0; i < image.GetWidth(); i++) {
            for (int j = 0; j < image.GetHeight(); j++) {
                RGB value = image.GetRGB(i, j);
                double cal = (Math.pow(0.299 * value.GetRed(), 2) + Math.pow(0.587 * value.GetGreen(), 2) + Math.pow(0.114 * value.GetBlue(), 2));
                double luminance = Math.sqrt(cal);
                list.add(luminance);
            }
        }
        //sort luminance in loop
        Collections.sort(list);

        int medianIndex = (list.size() /2);
        double median = list.get(medianIndex);

        //compare to luminance in another loop (calculate luminance again)
        for(int i = 0; i < image.GetWidth(); i++) {
            for (int j = 0; j < image.GetHeight(); j++) {
                RGB value = image.GetRGB(i, j);
                double cal = (Math.pow((0.299 * value.GetRed()), 2) + Math.pow((.587 * value.GetGreen()), 2) + Math.pow((.114 * value.GetBlue()), 2));
                double luminance = Math.sqrt(cal);

                if (luminance >= median) {
                    image.SetRGB(i, j, white);

                } else {
                    image.SetRGB(i, j, black);
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
        // Implement this method and remove the line below
        throw new UnsupportedOperationException();
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
        // Implement this method and remove the line below
        throw new UnsupportedOperationException();
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
        for(int i = 0; i < image.GetWidth(); i++){
            for(int j = 0; j < image.GetHeight(); j++){
               HSL hPixel = image.GetRGB(i, j).ConvertToHSL();
               hPixel.SetHue(hue);
               RGB rPixel = hPixel.GetRGB();
               image.SetRGB(i, j, rPixel);
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
        for(int i = 0; i < image.GetWidth(); i++){
            for(int j = 0; j < image.GetHeight(); j++){
                HSL hPixel = image.GetRGB(i, j).ConvertToHSL();
                hPixel.SetSaturation(saturation);
                RGB rPixel = hPixel.GetRGB();
                image.SetRGB(i, j, rPixel);
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
        for(int i = 0; i < image.GetWidth(); i++){
            for(int j = 0; j < image.GetHeight(); j++){
                HSL hPixel = image.GetRGB(i, j).ConvertToHSL();
                hPixel.SetLightness(lightness);
                RGB rPixel = hPixel.GetRGB();
                image.SetRGB(i, j, rPixel);
            }
        }
        return image;
    }
}
