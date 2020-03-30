import java.io.IOException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.jar.Pack200;

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
}
