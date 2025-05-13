package App.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainViewController {

    @FXML private PieChart pieChart;
    @FXML private BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        setupPieChart();
        setupBarChart();
    }

    private void setupPieChart() {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("飲食", 450),
                new PieChart.Data("交通", 200),
                new PieChart.Data("娛樂", 100)
        );
        pieChart.setData(pieData);
        pieChart.setTitle("支出比例");
    }

    private void setupBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("2025 收支");
        series.getData().add(new XYChart.Data<>("1月", 3000));
        series.getData().add(new XYChart.Data<>("2月", 2800));
        series.getData().add(new XYChart.Data<>("3月", 3100));
        series.getData().add(new XYChart.Data<>("4月", 2700));
        barChart.getData().add(series);
        barChart.setTitle("月支出總覽");
    }
}
