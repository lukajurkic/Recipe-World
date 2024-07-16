package hr.dice.luka_jurkic.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import hr.dice.luka_jurkic.rest.resources.RecipeResource;

import java.util.List;

public interface RecipeClient {

    @Headers("X-Api-Key: {access_token}")
    @RequestLine("GET ?query={recipeName}")
    List<RecipeResource> findByName(@Param("recipeName") String recipeName, @Param("access_token") String accessToken);
}
