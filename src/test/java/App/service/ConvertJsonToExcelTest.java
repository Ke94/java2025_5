package App.service;

import App.model.StorageManager;
import App.model.Transaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;

public class ConvertJsonToExcelTest {
    static public void main(String[] args) throws IOException {

        StorageManager storageManager = new StorageManager();
        storageManager.convertJsonToXlsx();
    }
}
