package App.service;
import App.ConfigLoader;
import dev.langchain4j.service.*;
import dev.langchain4j.model.openai.*;

public class openAIBot {
    private static final String apiKey = ConfigLoader.getKey("OPENAI_API_KEY");
    private static String data = "日圓: 當前=0.2134, 平均=0.2260, Z=-1.98, 離低點=4.37%";

    public static void main(String[] args) {
        AdviceAgent agent = AiServices.create(AdviceAgent.class, OpenAiChatModel.builder().apiKey(apiKey).modelName("gpt-4o").build());
        System.out.println(agent.generateReport(data));
    }

    public static String getAdvice(String exchangeData){
        AdviceAgent agent = AiServices.create(AdviceAgent.class, OpenAiChatModel.builder().apiKey(apiKey).modelName("gpt-4o").build());
        return agent.generateAdvice(exchangeData);
    }

    public static String getReport(String exchangeData){
        AdviceAgent agent = AiServices.create(AdviceAgent.class, OpenAiChatModel.builder().apiKey(apiKey).modelName("gpt-4o").build());
        return agent.generateReport(exchangeData);
    }
}
