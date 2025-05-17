package App.ForexData;
import App.ConfigLoader;
import java.io.*;
import java.net.*;
import java.util.*;
import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

public class ExchangeRateFetcher {

    private static final String apiKey = ConfigLoader.getKey("AlphaVantage_API_KEY");
    private static final String fromSymbol = "USD";
    private static final String toSymbol = "JPY";

    public static class Rate{
        @SerializedName("Open")
        public double open;
        @SerializedName("High")
        public double high;
        @SerializedName("Low")
        public double low;
        @SerializedName("Close")
        public double close;

        @Override
        public String toString(){
            return String.format("Open: %.3f, High: %.3f, Low: %.3f, Close: %.3f", open, high, low, close);
        }
    }



}
