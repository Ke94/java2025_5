package App.connection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExchangeRateApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/view/ExchangeRateView.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("匯率分析報告");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void launchApp() {
        new Thread(() -> Application.launch(ExchangeRateApp.class)).start();
    }
}
