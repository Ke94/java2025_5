package App.controller;

import App.DesktopNotifier;
import App.ForexData.ForexFetcher;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import App.service.*;
import App.ForexData.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, List<Double>> data = new HashMap<>();
        List<Double> rates = new ArrayList<>();

        //fetch exchange data
        data = ForexFetcher.loadExistingData();
        rates = data.get(currency);


        // Bot 建議
        botSuggestionArea.setText(openAIBot.getReport(ForexAnalyzer.analyze(data, currency)));
    }
}
