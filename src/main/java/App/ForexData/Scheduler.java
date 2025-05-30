package App.ForexData;

import java.io.IOException;
import java.util.*;
import App.service.*;
import App.DesktopNotifier;

public class Scheduler {

    public static void main(String[] args) {
        StringBuilder result = new StringBuilder();
        Map<String, List<Double>> data = new HashMap<>();

        //fetch newest exchange data
        try {
            data = ForexFetcher.fetchAndAppendLatest();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String currency : data.keySet()){
            result.append(ForexAnalyzer.analyze(data, currency));
        }
        DesktopNotifier.showMessage("匯率通知", openAIBot.getAdvice(result.toString()));

    }
}
