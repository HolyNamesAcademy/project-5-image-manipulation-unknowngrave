import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

/**
 * Controller is responsible for end-to-end execution of commands from the user.
 * Controller listens for commands from the user. Once the commands come in,
 * Controller parses the necessary information and delegates to ImageManipulator
 * to actually do image manipulation. Once ImageManipulator returns the transformed
 * image, Controller displays the image to the user.
 */
public class Controller {
    Img image;
    JFrame frame;

    /**
     * Create a UI window to display image
     */
    public Controller() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Wait for and execute commands from the user
     */
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

                System.out.println("Enter a command:");
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
                        System.out.println("Enter image save path:");
                        String path = scanner.next();

                        ImageManipulator.SaveImage(image, path);
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
                        System.out.println("Enter hue to set (0, 359):");
                        int hue = scanner.nextInt();
                        image = ImageManipulator.SetHue(image, hue);
                        break;
                    }
                    case "saturation": {
                        System.out.println("Enter saturation to set (0, 1):");
                        double saturation = scanner.nextDouble();
                        image = ImageManipulator.SetSaturation(image, saturation);
                        break;
                    }
                    case "lightness": {
                        System.out.println("Enter lightness to set (0, 1):");
                        double lightness = scanner.nextDouble();
                        image = ImageManipulator.SetLightness(image, lightness);
                        break;
                    }
                    case "quit": {
                        return;
                    }
                    default: {
                        System.out.println("Command not found.");
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

    /**
     * Removes the old image and draws a new image in the UI Window
     */
    public void DrawImage() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(image);

        // todo: fix the sizing of the UI window
        frame.setSize(image.GetScaledWidth() + 20, image.GetScaledHeight() + 20);
        frame.setVisible(true);
    }
}
