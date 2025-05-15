package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/view/MainView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 900, 600);
            stage.setScene(scene);
            stage.setTitle("理財管理小幫手");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();  // ✅ 一定要這行，這樣錯誤不會被吃掉
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
