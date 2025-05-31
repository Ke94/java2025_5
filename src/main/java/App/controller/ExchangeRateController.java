package App.controller;

import App.DesktopNotifier;
import App.ForexData.ForexFetcher;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import App.service.*;
import App.ForexData.*;

import java.io.IOException;
import java.util.*;

public class ExchangeRateController {
    @FXML private ComboBox<String> currencyComboBox;
    @FXML private LineChart<Integer, Number> rateChart;
    @FXML private TextArea botSuggestionArea;
    @FXML private NumberAxis yAxis;

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
        try{
            data = ForexFetcher.loadExistingData();
        }
        catch(IOException e){
            try{
                data = ForexFetcher.fetchAndAppendLatest();
            }
            catch(IOException err){
                e.printStackTrace();
            }
        }

        XYChart.Series<Integer, Number> series = new XYChart.Series<>();
        series.setName("TWD to " + currency);
        List<Double> l = data.get(currency).reversed();
        int i = 0;
        Double mn = Collections.min(l), mx = Collections.max(l);
        for(Double d : l){
            series.getData().add(new XYChart.Data<>(i++, d));
        }
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(mn);
        yAxis.setUpperBound(mx);
        yAxis.setTickUnit(0.5);

        rateChart.getData().clear();
        rateChart.getData().add(series);

        // Bot 建議
        botSuggestionArea.setText(openAIBot.getReport(ForexAnalyzer.analyze(data, currency)));
    }
}
// test