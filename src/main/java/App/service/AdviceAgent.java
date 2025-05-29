package App.service;
import dev.langchain4j.service.*;

public interface AdviceAgent {
    @SystemMessage("你是一位資深金融分析師，請根據提供的日圓匯率數據給出投資建議")
//    @UserMessage("最近匯率為：{{exchangeRate}}，過去平均匯率為：{{average}}，Z-score 為：{{zScore}}。請問是否建議買入？")
//    String generateAdvice(@V("exchangeRate") String exchangeRate, @V("average") String average, @V("zScore") String zScore);
    @UserMessage("以下是TWD對各幣別的最新資訊：\n{{exchangeData}}\n請給我簡短文字(約10個字)，指出最推薦買入哪個貨幣對，並簡要說明原因，如果沒有推薦買入的也可以")
    String generateAdvice(@V("exchangeData") String exchangeData);

    @SystemMessage("你是一位資深金融分析師，根據使用者提供的匯率分析報告進行詳細分析，並提供專業建議。請從數據面進行解析，條理分明，並使用適當的金融術語。")
    @UserMessage(
            """
            以下是使用TWD兌換某幣別的分析資料：
    
            {{report}}
    
            請你撰寫一份詳細報告，內容包含：
            1. 價格偏離情況分析（與平均價格的比較）
            2. Z-score 含義與風險評估
            3. 是否接近歷史低點的觀察與解釋
            4. 綜合是否建議買入，並解釋原因
            """
    )
    String generateReport(@V("report") String report);

}
