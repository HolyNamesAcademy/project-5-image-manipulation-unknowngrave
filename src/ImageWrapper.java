import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Created by hgotu on 3/11/2020.
 */
public class ImageWrapper extends JPanel {
    private BufferedImage image;

    public ImageWrapper(String imageFilePath) throws IOException {
        image = ImageIO.read(new File(imageFilePath));
    }

    public RGB GetRGB(int xVal, int yVal) {
        int rgb = image.getRGB(xVal, yVal);
        int red = (rgb >> 16) & 0x000000FF;
        int green = (rgb >> 8) & 0x000000FF;
        int blue = (rgb) & 0x000000FF;
        return new RGB(red, green, blue);
    }

    public void SetRGB(int xVal, int yVal, RGB rgb) {
        int rgbVal = ((rgb.GetRed() & 0x000000FF) << 16)
                        | ((rgb.GetGreen() & 0x000000FF) << 8)
                        | ((rgb.GetBlue() & 0x000000FF));
        image.setRGB(xVal, yVal, rgbVal);

    }

    public int GetWidth() {
        return image.getWidth();
    }

    public int GetHeight() {
        return image.getHeight();
    }

    public void Save(String format, String savePath) throws IOException {
        ImageIO.write(image, format, new File(savePath));
    }

    public int GetScaledWidth() { return (int) (800 * GetWidth() / (double) GetHeight());}

    public int GetScaledHeight() { return 800; }

    public void paint(Graphics g) {
        g.drawImage(image.getScaledInstance(GetScaledWidth(), GetScaledHeight(), java.awt.Image.SCALE_DEFAULT), 10, 10, this);
    }
}
