package hr.dice.luka_jurkic.config;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import hr.dice.luka_jurkic.client.RecipeClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecipeClientConfiguration {

    @Bean
    public RecipeClient recipeClientConfig() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(RecipeClient.class))
                .logLevel(Logger.Level.FULL)
                .target(RecipeClient.class, "https://api.api-ninjas.com/v1/recipe");
    }
}
