import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by hgotu on 3/19/2020.
 */
public class Controller {
    ImageWrapper image;
    JFrame frame;

    public Controller() {
        frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void Start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Commands:");
                System.out.println("\t'load'");
                System.out.println("\t'save'");
                System.out.println("\t'grayscale'");
                System.out.println("\t'invert'");
                System.out.println("\t'sepia'");
                System.out.println("\t'bw'");
                System.out.println("\t'rotate'");
                System.out.println("\t'instagram'");
                System.out.println("\t'hue'");
                System.out.println("\t'saturation'");
                System.out.println("\t'lightness'");
                System.out.println("\t'quit'");

                System.out.println("Enter a command ('help' to see list):");
                String command = scanner.next();

                switch (command) {
                    case "load": {
                        System.out.println("Enter image path:"); // todo: hagotur: deal with paths with spaces
                        String path = scanner.next();
                        image = ImageManipulator.LoadImage(path);
                        DrawImage();
                        break;
                    }
                    case "save": {
                        System.out.println("Enter image format (jpg, png, etc):");
                        String format = scanner.next();

                        System.out.println("Enter image save path:");
                        String path = scanner.next();

                        ImageManipulator.SaveImage(image, format, path);
                        break;
                    }
                    case "grayscale": {
                        image = ImageManipulator.ConvertToGrayScale(image);
                        DrawImage();
                        break;
                    }
                    case "invert": {
                        image = ImageManipulator.InvertImage(image);
                        break;
                    }
                    case "sepia": {
                        image = ImageManipulator.ConvertToSepia(image);
                        DrawImage();
                        break;
                    }
                    case "bw": {
                        image = ImageManipulator.ConvertToBW(image);
                        DrawImage();
                        break;
                    }
                    case "rotate": {
                        image = ImageManipulator.RotateImage(image);
                        DrawImage();
                        break;
                    }
                    case "instagram": {
                        image = ImageManipulator.InstagramFilter(image);
                        DrawImage();
                        break;
                    }
                    case "hue": {
                        System.out.println("Enter hue to add:");
                        int hue = scanner.nextInt();
                        image = ImageManipulator.AddHue(image, hue);
                        break;
                    }
                    case "saturation": {
                        System.out.println("Enter saturation to add:");
                        double saturation = scanner.nextDouble();
                        image = ImageManipulator.AddSaturation(image, saturation);
                        break;
                    }
                    case "lightness": {
                        System.out.println("Enter lightness to add:");
                        double lightness = scanner.nextDouble();
                        image = ImageManipulator.AddLightness(image, lightness);
                        break;
                    }
                    case "quit": {
                        return;
                    }
                    default: {
                        break;
                    }
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
            }
        }
    }

    public void DrawImage() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(image);

        frame.setSize(image.GetScaledWidth() + 20, image.GetScaledHeight() + 20);
        frame.setVisible(true);
    }
}
