package App.service;
import dev.langchain4j.service.*;

public interface AdviceAgent {
    @SystemMessage("你是一位資深金融分析師，請根據提供的日圓匯率數據給出簡潔的投資建議")
//    @UserMessage("最近匯率為：{{exchangeRate}}，過去平均匯率為：{{average}}，Z-score 為：{{zScore}}。請問是否建議買入？")
//    String generateAdvice(@V("exchangeRate") String exchangeRate, @V("average") String average, @V("zScore") String zScore);
    @UserMessage("以下是TWD對各幣別的最新資訊：\n{{exchangeData}}\n請給我簡短文字(約10個字)，指出最推薦買入哪個貨幣對，並簡要說明原因，如果沒有推薦買入的也可以")
    String generateAdvice(@V("exchangeData") String exchangeData);

}
