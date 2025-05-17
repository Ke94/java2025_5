package App;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.util.Map;
import java.util.HashMap;

public class CurrencyRateFetcher {
    private static final Map<String, String> mappingCurrency = new HashMap<>();
    static{
        mappingCurrency.put("USD", "美金");
        mappingCurrency.put("HKD", "港幣");
        mappingCurrency.put("GBP", "英鎊");
        mappingCurrency.put("AUD", "澳幣");
        mappingCurrency.put("CAD", "加拿大幣");
        mappingCurrency.put("SGD", "新加坡幣");
        mappingCurrency.put("CHF", "瑞士法郎");
        mappingCurrency.put("JPY", "日圓");
        mappingCurrency.put("ZAR", "南非幣 ");
        mappingCurrency.put("SEK", "瑞典幣");
        mappingCurrency.put("NZD", "紐元");
        mappingCurrency.put("THB", "泰幣");
        mappingCurrency.put("PHP", "菲國比索");
        mappingCurrency.put("IDR", "印尼幣");
        mappingCurrency.put("EUR", "歐元");
        mappingCurrency.put("KRW", "韓元");
        mappingCurrency.put("VND", "越南盾");
        mappingCurrency.put("MYR", "馬來幣");
        mappingCurrency.put("CNY", "人民幣");
    }
    public static String getRateAndSave(String currencyName){
        return "";
    }
    public static String fetchRate(String currencyName){
        try{
            Document doc = Jsoup.connect("https://rate.bot.com.tw/xrt?Lang=zh-TW").get();
            Elements rows = doc.select("tbody tr");

            String mappingName = mappingCurrency.getOrDefault(currencyName.toUpperCase(), null);
            if(mappingName == null) return "該幣別不支援";

            for(Element row : rows){
                String currentCurrency = rows.select("div.visible-phone print_hide").text().trim();
                String buyCurrency = rows.select("td[data-table=本行即期賣出].rate-content-sight").text().trim();
                if(currencyName.contains(mappingName)){
                    return buyCurrency;
                }
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return "not found";
    }
}
