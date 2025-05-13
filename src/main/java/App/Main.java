package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // 透過 FXMLLoader 載入 FXML 畫面
        Parent root = FXMLLoader.load(getClass().getResource("/app/view/MainView.fxml"));


        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("理財管理小幫手");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
