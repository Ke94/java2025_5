package App.ForexData;

import java.io.IOException;
import java.util.*;
import App.service.*;
import App.DesktopNotifier;

public class Scheduler {

    public static void main(String[] args) {
        try {
            Map<String, List<Double>> data = ForexFetcher.fetchAndAppendLatest();
            DesktopNotifier.showMessage("匯率通知", openAIBot.getAdvice(ForexAnalyzer.analyze(data)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
