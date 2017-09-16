import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
    public static void main(String[] args) {
    launch(args);
}

    public void start(Stage primaryStage) {
        primaryStage.setTitle("ML Brain Data");

        Controller.setStage(primaryStage);

        try
        {
            Parent page = FXMLLoader.load(getClass().getResource("View.fxml"));

            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}