package App.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Transaction的儲存管理器，可以load或save json檔案
 */
public class StorageManager {
    final private Gson gson;

    public StorageManager(){
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDataGsonAdapter())
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
}
