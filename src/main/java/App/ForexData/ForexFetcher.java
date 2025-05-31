package App.ForexData;

import App.ConfigLoader;
import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ForexFetcher {
    private static final String API_KEY = ConfigLoader.getKey("AlphaVantage_API_KEY");
    private static final String BASE_CURRENCY = "TWD";
    private static final String[] TARGET_CURRENCIES = {"USD", "JPY", "EUR", "AUD", "CAD"};
    private static final String SAVE_PATH = "src/main/java/App/ForexData/exchange_data.json";

    public static Map<String, List<Double>> fetchAndAppendLatest() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, List<Double>> allRates = new HashMap<>();

        for (String target : TARGET_CURRENCIES) {
            if (target.equals(BASE_CURRENCY)) continue;

            String urlStr = String.format(
                    "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=%s&to_symbol=%s&apikey=%s&outputsize=compact",
                    BASE_CURRENCY, target, API_KEY);

            try {
                String json = HttpUtil.fetchJson(urlStr);
                JsonObject jsonObj = JsonParser.parseString(json).getAsJsonObject();

                JsonObject timeSeries = jsonObj.getAsJsonObject("Time Series FX (Daily)");
                if (timeSeries == null) throw new IllegalStateException("Missing FX data");

                List<String> sortedDates = new ArrayList<>(timeSeries.keySet());
                Collections.sort(sortedDates, Collections.reverseOrder());

                List<Double> rateList = new ArrayList<>();
                for (String date : sortedDates) {
                    JsonObject daily = timeSeries.getAsJsonObject(date);
                    double close = daily.get("4. close").getAsDouble();
                    rateList.add(close);
                }

                allRates.put(target, rateList);

            } catch (Exception e) {
                System.err.println("Error fetching " + BASE_CURRENCY + "/" + target + ": " + e.getMessage());
            }
        }

        String outputJson = gson.toJson(allRates);
        Files.write(Paths.get(SAVE_PATH), outputJson.getBytes());
        return allRates;
    }

    public static Map<String, List<Double>> loadExistingData() throws IOException{
//        try {
            String json = new String(Files.readAllBytes(Paths.get(SAVE_PATH)));
            Gson gson = new Gson();
            Map<String, List<Double>> map = new HashMap<>();
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
            for (String key : obj.keySet()) {
                JsonArray array = obj.getAsJsonArray(key);
                List<Double> list = new ArrayList<>();
                for (JsonElement e : array) list.add(e.getAsDouble());
                map.put(key, list);
            }
            return map;
//        } catch (IOException e) {
//            return new HashMap<>();
//        }
    }
}
