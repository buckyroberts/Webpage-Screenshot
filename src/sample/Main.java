package sample;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {

    private static String imageName, url;
    private Browser browser;
    private Timer timer = new java.util.Timer();

    public static void main(String[] args){
        imageName = args[0];
        url = args[1];
        System.setProperty("jsse.enableSNIExtension", "false");
        System.out.println("Creating screenshot for " + url);
        launch(args);
    }

    @Override
    public void start(Stage window) {
        window.setTitle(url);

        browser = new Browser(url);
        monitorPageStatus();

        VBox layout = new VBox();
        layout.getChildren().addAll(browser);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.setOnCloseRequest(we -> System.exit(0));
        window.show();
    }

    private void monitorPageStatus(){
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    if(browser.isPageLoaded()){
                        System.out.println("Page now loaded, taking screenshot...");
                        saveAsPng();
                        cancel();
                    }
                    else
                        System.out.println("Loading page...");
                });
            }
        }, 1000, 1000);
    }

    @FXML
    private void saveAsPng() {
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    WritableImage image = browser.snapshot(new SnapshotParameters(), null);
                    File file = new File("images/" + imageName + ".png");
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                        System.out.println("Screenshot saved as " + imageName + ".png");
                        System.exit(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(0);
                    }
                    cancel();
                });
            }
        }, 5000);
    }

}
