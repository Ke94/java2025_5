package App.ForexData;
import java.util.*;

public class ForexAnalyzer {

    public static String analyze(Map<String, List<Double>> data){
        StringBuilder result = new StringBuilder();
        for(String currency : data.keySet()){
            List<Double> rates = data.get(currency);

            double current = rates.getFirst();
            double mean = rates.stream().mapToDouble(n -> n).average().orElse(0);
            double min = rates.stream().mapToDouble(n -> n).min().orElse(0);
            double stdDev = calcStdDev(rates, mean);
            double zScore = (current - mean) / stdDev;
            double valueGap = (current - min) / min * 100;

            result.append(String.format(Locale.US, "%s: 當前=%.4f, 平均=%.4f, Z=%.2f, 離低點=%.2f%%\n", currency, current, mean, zScore, valueGap));
        }
        return result.toString();
    }

    public static double calcStdDev(List<Double> data, double mean){
        double sum = 0;
        for(double num : data){
            sum += Math.pow((num - mean), 2);
        }
        return Math.sqrt(sum / data.size());
    }
}
