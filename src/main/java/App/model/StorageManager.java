package App.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.File;

/**
 * Transaction的儲存管理器，可以load或save json檔案
 */
public class StorageManager {
    final private Gson gson;

    public StorageManager(){
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateGsonAdapter())
                .setPrettyPrinting()
                .create();
    }

    /**
     * 將List存成json檔
     * @param list 一個要存檔的List
     */
    public void saveTransactions(List<Transaction> list) {
        try(FileWriter writer = new FileWriter("transactions.json")){
            gson.toJson(list, writer);
        }
        catch (IOException err){
            System.out.println((err.getMessage()));
        }
    }

    /**
     * 將json檔案載入成List
     */
    public List<Transaction> loadTransactions() {
        try {
            Type listType = new TypeToken<List<Transaction>>(){}.getType();
            return gson.fromJson(new FileReader("transactions.json"), listType);
        }
        catch (IOException err){
            System.out.println(err.getMessage());
            return new ArrayList<Transaction>();
        }
    }

    /**
     * 將Json檔轉成xlsx
     * @throws IOException 檔案開啟失敗
     */
    public void convertJsonToXlsx(File file) throws IOException{
        Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
        List<Map<String, Object>> data = gson.fromJson(new FileReader("transactions.json"), listType);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        Set<String> keys = data.getFirst().keySet();
        List<String> keyList = new ArrayList<>(keys);

        Row header = sheet.createRow(0);
        for (int i = 0; i < keyList.size(); i++) {
            header.createCell(i).setCellValue(keyList.get(i));
        }

        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Map<String, Object> record = data.get(i);
            for (int j = 0; j < keyList.size(); j++) {
                Object value = record.get(keyList.get(j));
                row.createCell(j).setCellValue(value != null ? value.toString() : "");
            }
        }
        try (FileOutputStream fos = new FileOutputStream("transactions.xlsx")) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        workbook.close();
    }

    public List<Transaction> loadTransactionsFromFile(File file) throws IOException {
        Type listType = new TypeToken<List<Transaction>>(){}.getType();
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, listType);
        }
    }

    public void saveTransactionsToFile(File file, List<Transaction> list) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(list, writer);
        }
    }
}
