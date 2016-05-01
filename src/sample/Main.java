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
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {

    private String pageId, url;
    private Browser browser;
    private Timer timer = new java.util.Timer();

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage window) {
        window.setTitle("Screenshot Capture");

        readUrl();
        browser = new Browser(url);
        monitorPageStatus();

        VBox layout = new VBox();
        layout.getChildren().addAll(browser);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.setOnCloseRequest(we -> System.exit(0));
        window.show();
    }

    private void readUrl() {
        try{
            URL oracle = new URL("https://raw.githubusercontent.com/buckyroberts/Webpage-Screenshot/master/sample-link.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
            if((pageId = in.readLine()) != null && (url = in.readLine()) != null){
                System.out.println("Page ID: " + pageId);
                System.out.println("URL: " + url);
            }
            in.close();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void monitorPageStatus(){
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    if(browser.isPageLoaded()){
                        System.out.println("Page is now loaded");
                        saveAsPng();
                        cancel();
                    }
                    else
                        System.out.println("Page not loaded...");
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
                    File file = new File("images/" + pageId + ".png");
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                        System.out.println("Screenshot saved");
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
