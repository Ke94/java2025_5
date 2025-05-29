package App.controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import App.model.ListOfTransaction;
import App.model.StorageManager;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import App.model.Transaction;
import javafx.scene.control.*;
import App.model.KindOfTransaction;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Pair;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.List;
import java.io.IOException;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class MainViewController {
    private ListOfTransaction listOfTransaction = new ListOfTransaction();
//    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
    private ListOfTransaction transactionList = new ListOfTransaction();
    private StorageManager storageManager = new StorageManager();

    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, String> dateColumn;
    @FXML private TableColumn<Transaction, String> kindColumn;
    @FXML private TableColumn<Transaction, String> categoryColumn;
    @FXML private TableColumn<Transaction, Number> amountColumn;
    @FXML private TableColumn<Transaction, String> noteColumn;

    @FXML private ComboBox<Integer> yearComboBox;
    @FXML private ComboBox<String> monthComboBox;
    @FXML private ComboBox<KindOfTransaction> kindComboBox;

    @FXML private PieChart pieChart;
    @FXML private BarChart<String, Number> barChart;
    @FXML private Label balanceLabel;
    @FXML private ToggleGroup chartGroup;
    @FXML private ToggleButton pieToggle;
    @FXML private ToggleButton barToggle;


    @FXML
    public void initialize() {
        // 讀取存檔
        listOfTransaction = new ListOfTransaction(storageManager.loadTransactions());
//        transactionList.setAll(listOfTransaction.getList());
        transactionList.setAll(listOfTransaction);
        // 綁定 TableView 的資料來源
        transactionTable.setItems(transactionList.getList());

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
        // 改成包含「全部」與 1~12
        monthComboBox.getItems().add("全部");
        for (int i = 1; i <= 12; i++) {
            monthComboBox.getItems().add(i + "月");
        }




        kindComboBox.getItems().add(null);  // null 當作「全部」
        kindComboBox.getItems().addAll(KindOfTransaction.values());
        kindComboBox.setPromptText("全部");

        yearComboBox.setValue(2025);
        monthComboBox.setValue("全部");
        kindComboBox.setValue(KindOfTransaction.EXPENSES);

        setupPieChart();
        setupBarChart();

        updateBalance();

        showPieChart();
        ToggleGroup chartGroup = pieToggle.getToggleGroup(); // 切換圖表的按鈕組
        chartGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                ToggleButton selected = (ToggleButton) newToggle;
                System.out.println("現在選中的是：" + selected.getText());
            }
        });
    }


    private void setupPieChart() {
        pieChart.getData().clear();
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        List<Pair<String, Integer>> m = transactionList.getCategoryAndAmount();
        m.sort(((l, r) -> r.getValue() - l.getValue()));
        int sum = 0;
        for (Pair<String, Integer> o : m) {
            pieData.add(new PieChart.Data(o.getKey(), o.getValue()));
            sum += o.getValue();
//            System.out.println(o.getKey() + ": " + o.getValue());
        }
        int finalSum = sum;
        pieData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(),
                                String.format(" %.2f%%", data.pieValueProperty().getValue()/(double)finalSum*100)
                        )
                ));
        pieChart.setStartAngle(90);
        pieChart.setData(pieData);
        pieChart.setTitle("支出比例");
    }

    private void showPieChart(){
        setupPieChart();
        pieChart.setVisible(true);
        barChart.setVisible(false);
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
    private void showBarChart(){
        pieChart.setVisible(false);
        barChart.setVisible(true);
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
        DatePicker datePicker = new DatePicker();  // 日期選擇器
        datePicker.setValue(LocalDate.now());      // 預設為今天

        VBox content = new VBox(10, datePicker, kindBox, categoryBox, amountField, noteField);

        dialog.getDialogPane().setContent(content);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // 點 OK 時產生 Transaction
        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    int amount = Integer.parseInt(amountField.getText());
                    LocalDate date = datePicker.getValue();
                    return new Transaction(date, kindBox.getValue(), categoryBox.getValue(), amount, noteField.getText());
                } catch (NumberFormatException e) {
                    // 可以改用提示
                    System.out.println("請輸入正確金額");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(transaction -> {
            listOfTransaction.add(transaction);   // 只加到 ListOfTransaction
            transactionList.setAll(listOfTransaction.getList());  //  重新同步 TableView
            storageManager.saveTransactions(listOfTransaction.getList());
            updateBalance();
        });

    }

    @FXML
    private void onDeleteButtonClicked() {
        Transaction selected = transactionTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            listOfTransaction.remove(selected);
            transactionList.setAll(listOfTransaction.getList());  // 同步刷新
            storageManager.saveTransactions(listOfTransaction.getList());
        } else {
            System.out.println("請先選擇要刪除的資料");
        }
        updateBalance();
    }

    @FXML
    private void onEditButtonClicked() {

        Transaction selected = transactionTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            System.out.println("請選擇要編輯的項目");
            return;
        }
        DatePicker datePicker = new DatePicker(selected.getCreatedTime());
        Dialog<Transaction> dialog = new Dialog<>();
        dialog.setTitle("編輯記帳");

        // 建立 UI
        ComboBox<KindOfTransaction> kindBox = new ComboBox<>();
        kindBox.getItems().addAll(KindOfTransaction.values());
        kindBox.setValue(selected.getKind());

        ComboBox<String> categoryBox = new ComboBox<>();
        updateCategoryBox(kindBox.getValue(), categoryBox);
        categoryBox.setValue(selected.getCategory());

        kindBox.setOnAction(e -> updateCategoryBox(kindBox.getValue(), categoryBox));

        TextField amountField = new TextField(String.valueOf(selected.getAmount()));
        TextField noteField = new TextField(selected.getNote());

        VBox content = new VBox(10, datePicker, kindBox, categoryBox, amountField, noteField);

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    int amount = Integer.parseInt(amountField.getText());
                    LocalDate date = datePicker.getValue() != null ? datePicker.getValue() : selected.getCreatedTime();
                    return new Transaction(date, kindBox.getValue(), categoryBox.getValue(), amount, noteField.getText());
                } catch (NumberFormatException e) {
                    System.out.println("請輸入正確金額");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(newTransaction -> {
            /// 找出在 listOfTransaction 的位置
            int index = listOfTransaction.getList().indexOf(selected);

            if (index != -1) {
                transactionList.remove(selected);
                listOfTransaction.remove(index);
            }

            // 加入新的
            transactionList.add(newTransaction);
            listOfTransaction.add(newTransaction);

            storageManager.saveTransactions(listOfTransaction.getList());
            transactionTable.refresh();
            updateBalance();
        });
    }

    @FXML
    private void onFilterButtonClicked() {
        int selectedYear = yearComboBox.getValue();
        String selectedMonthStr = monthComboBox.getValue();
        Integer selectedMonth = null;
        if (!"全部".equals(selectedMonthStr)) {
            selectedMonth = Integer.parseInt(selectedMonthStr.replace("月", ""));
        }

        KindOfTransaction selectedKind = kindComboBox.getValue(); // 可能是 null

        ListOfTransaction filtered = listOfTransaction.selectByYear(selectedYear);

        if (selectedMonth != null) {
            filtered = filtered.selectByMonth(selectedMonth);
        }

        if (selectedKind != null) {
            filtered = new ListOfTransaction(
                    filtered.getList().stream()
                            .filter(t -> t.getKind() == selectedKind)
                            .toList()
            );
        }

        var sortedList = filtered.getList().stream()
                .sorted(Comparator.comparing(Transaction::getCreatedTime))
                .toList();

        transactionList.setAll(sortedList);

        showPieChart();
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

    @FXML
    private void onImportButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("選擇要匯入的 JSON 檔案");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            List<Transaction> imported;
            try {
                imported = storageManager.loadTransactionsFromFile(selectedFile);
                listOfTransaction = new ListOfTransaction(imported);
                transactionList.setAll(imported);
                System.out.println("匯入成功：" + selectedFile.getName());
            } catch (IOException e) {
                System.out.println("匯入失敗：" + e.getMessage());
            }
        }
    }

    @FXML
    private void onExportButtonClicked() {
        List<String> options = List.of("JSON", "Excel");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("JSON", options);
        dialog.setTitle("選擇匯出格式");
        dialog.setHeaderText("請選擇要匯出的檔案格式");
        dialog.setContentText("格式：");

        dialog.showAndWait().ifPresent(format -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("選擇匯出位置");

            if (format.equals("JSON")) {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            } else if (format.equals("Excel")) {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            }

            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                try {
                    if (format.equals("JSON")) {
                        storageManager.saveTransactionsToFile(file, listOfTransaction.getList());
                        System.out.println("匯出 JSON 成功");
                    } else if (format.equals("Excel")) {
                        storageManager.convertJsonToXlsx(file); // 傳入目的地 file
                        System.out.println("匯出 Excel 成功");
                    }
                } catch (IOException e) {
                    System.out.println("匯出失敗：" + e.getMessage());
                }
            }
        });
    }

    private void updateBalance() {
        int balance = listOfTransaction.getList().stream()
                .mapToInt(Transaction::getAmountWithKind)
                .sum();

        balanceLabel.setText("目前剩餘金額：" + balance + " 元");
    }

    @FXML
    private void onExchangeRateButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/view/ExchangeRateView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("匯率查詢");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
