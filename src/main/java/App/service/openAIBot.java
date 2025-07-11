package App.service;
import App.ConfigLoader;
import dev.langchain4j.service.*;
import dev.langchain4j.model.openai.*;
import App.ForexData.*;

public class openAIBot {
    private static final String apiKey = ConfigLoader.getKey("OPENAI_API_KEY");


    public static String getAdvice(String exchangeData){
        AdviceAgent agent = AiServices.create(AdviceAgent.class, OpenAiChatModel.builder().apiKey(apiKey).modelName("gpt-4o").build());
        return agent.generateAdvice(exchangeData);
    }

    public static String getReport(String exchangeData){
        AdviceAgent agent = AiServices.create(AdviceAgent.class, OpenAiChatModel.builder().apiKey(apiKey).modelName("gpt-4o").build());
        return agent.generateReport(exchangeData);
    }
}
