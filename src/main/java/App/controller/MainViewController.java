package App.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import App.model.Transaction;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ComboBox;
import App.model.KindOfTransaction;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleIntegerProperty;

public class MainViewController {
    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, String> dateColumn;
    @FXML private TableColumn<Transaction, String> kindColumn;
    @FXML private TableColumn<Transaction, String> categoryColumn;
    @FXML private TableColumn<Transaction, Number> amountColumn;
    @FXML private TableColumn<Transaction, String> noteColumn;
    @FXML private ComboBox<Integer> yearComboBox;
    @FXML private ComboBox<Integer> monthComboBox;
    @FXML private ComboBox<KindOfTransaction> kindComboBox;

    @FXML private PieChart pieChart;
    @FXML private BarChart<String, Number> barChart;


    @FXML
    public void initialize() {
        // 綁定 TableView 的資料來源
        transactionTable.setItems(transactionList);

        // TableColumn 綁定資料
        dateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCreatedTime().toString())
        );
        kindColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getKind().toString())
        );
        amountColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getAmount())
        );
        categoryColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCategory())
        );
        noteColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNote())
        );

        yearComboBox.getItems().addAll(2024, 2025);
        monthComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        kindComboBox.getItems().addAll(KindOfTransaction.values());

        yearComboBox.setValue(2025);
        monthComboBox.setValue(4);
        kindComboBox.setValue(KindOfTransaction.EXPENSES);

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

    @FXML
    private void onAddButtonClicked() {
        Dialog<Transaction> dialog = new Dialog<>();
        dialog.setTitle("新增記帳");

        // 建立 UI
        ComboBox<KindOfTransaction> kindBox = new ComboBox<>();
        kindBox.getItems().addAll(KindOfTransaction.values());
        kindBox.setValue(KindOfTransaction.EXPENSES);

        ComboBox<String> categoryBox = new ComboBox<>();
        updateCategoryBox(kindBox.getValue(), categoryBox);  // 初始化

        kindBox.setOnAction(e -> {
            updateCategoryBox(kindBox.getValue(), categoryBox);
        });


        TextField amountField = new TextField();
        amountField.setPromptText("金額");

        TextField noteField = new TextField();
        noteField.setPromptText("註解");

        VBox content = new VBox(10, kindBox, categoryBox, amountField, noteField);

        dialog.getDialogPane().setContent(content);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // 點 OK 時產生 Transaction
        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    int amount = Integer.parseInt(amountField.getText());
                    return new Transaction(kindBox.getValue(), categoryBox.getValue(), amount, noteField.getText());
                } catch (NumberFormatException e) {
                    // 可以改用提示
                    System.out.println("請輸入正確金額");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(transaction -> {
            transactionList.add(transaction);  //  只改 list
        });

    }

    private void updateCategoryBox(KindOfTransaction kind, ComboBox<String> categoryBox) {
        categoryBox.getItems().clear();
        if (kind == KindOfTransaction.INCOME) {
            categoryBox.getItems().addAll("薪水", "投資", "獎金");
        } else {
            categoryBox.getItems().addAll("飲食", "交通", "娛樂");
        }
        categoryBox.setValue(categoryBox.getItems().get(0));
    }


}
