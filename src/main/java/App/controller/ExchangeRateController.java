package App.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class ExchangeRateController {

    @FXML private ComboBox<String> currencyComboBox;
    @FXML private LineChart<String, Number> rateChart;
    @FXML private TextArea botSuggestionArea;

    @FXML
    public void initialize() {
        // 加入幣別選項
        currencyComboBox.getItems().addAll("USD", "JPY", "EUR", "GBP", "AUD");
        currencyComboBox.setValue("USD");

        // 預設提示
        botSuggestionArea.setText("請選擇幣別並點擊查詢以查看台幣匯率趨勢。");
    }

    @FXML
    private void onQueryButtonClicked() {
        String currency = currencyComboBox.getValue();

        // 假資料
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("TWD to " + currency);
        series.getData().add(new XYChart.Data<>("5/01", 0.032));
        series.getData().add(new XYChart.Data<>("5/08", 0.0315));
        series.getData().add(new XYChart.Data<>("5/15", 0.0318));
        series.getData().add(new XYChart.Data<>("5/22", 0.0312));

        rateChart.getData().clear();
        rateChart.getData().add(series);

        // Bot 建議
        botSuggestionArea.setText("最近台幣兌 " + currency + " 匯率略有波動，建議觀察趨勢再決定是否兌換。");
    }
}
