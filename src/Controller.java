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
                System.out.println("\t'grayscale'");
                System.out.println("\t'sepia'");
                System.out.println("\t'bw'");
                System.out.println("\t'save'");
                System.out.println("\t'quit'");

                System.out.println("Enter a command ('help' to see list):");
                String command = scanner.next();

                switch (command) {
                    case "load": {
                        System.out.println("Enter image path:");
                        String path = scanner.next();
                        image = ImageManipulator.LoadImage(path);
                        break;
                    }
                    case "grayscale": {
                        image = ImageManipulator.ConvertToGrayScale(image);
                        break;
                    }
                    case "sepia": {
                        image = ImageManipulator.ConvertToSepia(image);
                        break;
                    }
                    case "bw": {
                        image = ImageManipulator.ConvertToBW(image);
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
                    case "quit": {
                        return;
                    }
                    default: {
                        break;
                    }
                }

                DrawImage();
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
