package App.service;
import App.ConfigLoader;
import dev.langchain4j.service.*;
import dev.langchain4j.model.openai.*;

public class openAIBot {
    private static final String apiKey = ConfigLoader.getKey("OPENAI_API_KEY");

    public static void main(String[] args){
        AdviceAgent agent = AiServices.create(AdviceAgent.class, OpenAiChatModel.builder().apiKey(apiKey).modelName("gpt-4o").build());
        System.out.println(agent.generateAdvice("0.1", "0.2", "0.3"));
    }

    public static String getAdvice(String para1, String para2, String para3){
        AdviceAgent agent = AiServices.create(AdviceAgent.class, OpenAiChatModel.builder().apiKey(apiKey).modelName("gpt-4o").build());
        return agent.generateAdvice(para1, para2, para3);
    }
}
