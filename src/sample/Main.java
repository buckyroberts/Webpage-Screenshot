package sample;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Main extends Application {

    private Browser browser;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage window) {
        window.setTitle("Screenshot Capture");
        Button button = new Button("Take screenshot");
        button.setOnAction(e -> saveAsPng());

        browser = new Browser("https://thenewboston.com/");

        VBox layout = new VBox();
        layout.getChildren().addAll(button, browser);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void saveAsPng() {
        WritableImage image = browser.snapshot(new SnapshotParameters(), null);
        File file = new File("sample.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            System.out.println("Nice");
        } catch (IOException e) {
            System.out.println("Crap");
        }
    }

}

