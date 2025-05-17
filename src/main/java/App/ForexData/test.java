package App.ForexData;
import App.ConfigLoader;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import com.google.gson.*;

public class test {
    private static final String API_KEY = ConfigLoader.getKey("AlphaVantage_API_KEY");
    private static final String BASE_CURRENCY = "TWD";
    private static final String[] TARGET_CURRENCIES = {"USD", "JPY", "EUR", "AUD", "CAD"};
    private static final String SAVE_PATH = "src/main/java/App/ForexData/exchange_data.json";

    public static void main(String[] args) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, List<Double>> allRates = new HashMap<>();

        for (String target : TARGET_CURRENCIES) {
            if (target.equals(BASE_CURRENCY)) continue;
            String from = BASE_CURRENCY;
            String to = target;
            String urlStr = String.format(
                    "https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=%s&to_symbol=%s&apikey=%s&outputsize=full",
                    from, to, API_KEY);

            try {
                String json = fetchJson(urlStr);
                JsonObject jsonObj = JsonParser.parseString(json).getAsJsonObject();
                JsonObject timeSeries = jsonObj.getAsJsonObject("Time Series FX (Daily)");

                List<Double> closeRates = new ArrayList<>();
                for (Map.Entry<String, JsonElement> entry : timeSeries.entrySet()) {
                    JsonObject daily = entry.getValue().getAsJsonObject();
                    double close = daily.get("4. close").getAsDouble();
                    closeRates.add(close);
                }

                allRates.put(to, closeRates);

            } catch (Exception e) {
                System.err.println("Error fetching " + from + "/" + to + ": " + e.getMessage());
            }
        }

        // Save to JSON
        String outputJson = gson.toJson(allRates);
        Files.write(Paths.get(SAVE_PATH), outputJson.getBytes());
        System.out.println("Data saved to " + SAVE_PATH);

        // Analyze
        analyze(allRates);
    }

    private static String fetchJson(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private static void analyze(Map<String, List<Double>> data) {
        for (String currency : data.keySet()) {
            List<Double> rates = data.get(currency);
            if (rates.size() < 30) continue;

            double current = rates.get(0);
            double mean = rates.stream().mapToDouble(d -> d).average().orElse(0);
            double min = rates.stream().mapToDouble(d -> d).min().orElse(0);
            double std = calcStdDev(rates, mean);
            double zScore = (current - mean) / std;
            double distanceToMin = (current - min) / min * 100;

            System.out.printf(Locale.US, "%s: 當前=%.4f, 平均=%.4f, Z=%.2f, 離低點=%.2f%%\n",
                    currency, current, mean, zScore, distanceToMin);
        }
    }

    private static double calcStdDev(List<Double> data, double mean) {
        double sum = 0;
        for (double d : data) {
            sum += Math.pow(d - mean, 2);
        }
        return Math.sqrt(sum / data.size());
    }
}
