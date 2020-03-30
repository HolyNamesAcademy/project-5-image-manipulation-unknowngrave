import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by hgotu on 3/19/2020.
 */
public class ImageManipulator {
    public static ImageWrapper LoadImage(String path) throws IOException {
        return new ImageWrapper(path);
    }

    public static ImageWrapper ConvertToGrayScale(ImageWrapper image) {
        for (int i = 0; i < image.GetHeight(); i++) {
            for (int j = 0; j < image.GetWidth(); j++) {
                RGB rgb = image.GetRGB(j, i);
                int avg = (rgb.GetRed() + rgb.GetGreen() + rgb.GetBlue()) / 3;
                image.SetRGB(j, i, new RGB(avg, avg, avg));
            }
        }

        return image;
    }

    public static ImageWrapper ConvertToSepia(ImageWrapper image) {
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

    public static ImageWrapper ConvertToBW(ImageWrapper image) {
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

    public static void SaveImage(ImageWrapper image, String format, String path) throws IOException {
        image.Save(format, path);
    }

    public  static ImageWrapper ConstructedImage(int xWidth, int xHeight) {
        ImageWrapper image = new ImageWrapper(xWidth, xHeight);
        int h = image.GetHeight();
        int w = image.GetWidth();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int r = (i * image.GetWidth() + j) / 256;
                int g = (i + j * image.GetHeight()) / 256;
                int b = 255 - ((i *  128 / h) + (j * 128 / w));

                image.SetRGB(j, i, new RGB(r, g, b));
            }
        }
        return image;
    }

    public  static ImageWrapper RotateImage(ImageWrapper image) {
        ImageWrapper rotatedImage = new ImageWrapper(image.GetHeight(), image.GetWidth());
        for (int i = 0; i < rotatedImage.GetHeight(); i++) {
            for (int j = 0; j < rotatedImage.GetWidth(); j++) {
                RGB oldPixel = image.GetRGB(i, image.GetHeight() - j - 1);
                rotatedImage.SetRGB(j, i, oldPixel);
            }
        }
        return rotatedImage;
    }

    public static ImageWrapper InvertImage(ImageWrapper image) {
        for (int i = 0; i < image.GetHeight(); i++) {
            for (int j = 0; j < image.GetWidth(); j++) {
                RGB pixel = image.GetRGB(j, i);
                RGB invertedPixel = new RGB(255 - pixel.GetRed(), 255 - pixel.GetGreen(), 255 - pixel.GetBlue());
                image.SetRGB(j, i, invertedPixel);
            }
        }

        return image;
    }

    public static int truncate(int value) {
        if (value < 0)
        {
            return 0;
        }

        if (value > 255) {
            return 255;
        }

        return value;
    }

    public static ImageWrapper InstagramFilter(ImageWrapper image) throws IOException {
        // apply transformations
        for (int i = 0; i < image.GetHeight(); ++i) {
            for (int j = 0; j < image.GetWidth(); j++) {
                image.SetRGB(j, i, applyTransform(image.GetRGB(j, i)));
            }
        }

        // add halo overlay
        ImageWrapper halo = new ImageWrapper("resources/halo.png");
        for (int i = 0; i < image.GetHeight(); ++i) {
            for (int j = 0; j < image.GetWidth(); j++) {
                RGB haloPixel = halo.GetRGB(j * halo.GetWidth() / image.GetWidth(), i * halo.GetHeight() / image.GetHeight());
                RGB imagePixel = image.GetRGB(j, i);
                imagePixel.SetRed(truncate((int) (0.65 * imagePixel.GetRed() + 0.35 * haloPixel.GetRed()) + 20));
                imagePixel.SetGreen(truncate((int) (0.65 * imagePixel.GetGreen() + 0.35 * haloPixel.GetGreen()) + 20));
                imagePixel.SetBlue(truncate((int) (0.65 * imagePixel.GetBlue() + 0.35 * haloPixel.GetBlue()) + 20));

                image.SetRGB(j, i, imagePixel);
            }
        }

        // add decorative grain
        ImageWrapper grain = new ImageWrapper("resources/decorative_grain.png");
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
        int r = rgb.GetRed();
        int g = (int) (rgb.GetGreen() / 1.5);
        int b = (int) (rgb.GetBlue() / 3);

        return new RGB(r, g, b);
    }

    public static ImageWrapper AddLightness(ImageWrapper image, double lightness) {
        for (int i = 0; i < image.GetHeight(); ++i) {
            for (int j = 0; j < image.GetWidth(); j++) {
                HSL hsl = image.GetRGB(j, i).GetHSL();
                hsl.SetLightness(hsl.GetLightness() + lightness);
                image.SetRGB(j, i, hsl.GetRGB());
            }
        }

        return image;
    }

    public static ImageWrapper AddSaturation(ImageWrapper image, double saturation) {
        for (int i = 0; i < image.GetHeight(); ++i) {
            for (int j = 0; j < image.GetWidth(); j++) {
                HSL hsl = image.GetRGB(j, i).GetHSL();
                hsl.SetSaturation(hsl.GetSaturation() + saturation);
                image.SetRGB(j, i, hsl.GetRGB());
            }
        }

        return image;
    }

    public static ImageWrapper AddHue(ImageWrapper image, int hue) {
        for (int i = 0; i < image.GetHeight(); ++i) {
            for (int j = 0; j < image.GetWidth(); j++) {
                HSL hsl = image.GetRGB(j, i).GetHSL();
                hsl.SetHue(hsl.GetHue() + hue);
                image.SetRGB(j, i, hsl.GetRGB());
            }
        }

        return image;
    }
}
