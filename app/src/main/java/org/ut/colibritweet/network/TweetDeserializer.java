package org.ut.colibritweet.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.ut.colibritweet.pojo.Tweet;
import org.ut.colibritweet.pojo.User;

import java.lang.reflect.Type;

public class TweetDeserializer implements JsonDeserializer {

    // работа с формирующим Gson объектом  для автоматического формирования
    private static final Gson GSON = new Gson();

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject tweetJson = json.getAsJsonObject();

        Tweet tweet = GSON.fromJson(tweetJson, Tweet.class);

        String imageUrl = getTweetImageUrl(tweetJson);
        tweet.setImageUrl(imageUrl);


        return tweet;
    }

    //все методы заменены для работы с библиотекой gson меняем объект JsonObject tweetJson
    // JSONObject entities = tweetJson.getJSONObject("entities");
    //
    private String getTweetImageUrl(JsonObject tweetJson) {
        JsonObject entities = tweetJson.get("entities").getAsJsonObject();
        JsonArray mediaArray = entities.has("media") ? entities.get("media").getAsJsonArray() : null;
        JsonObject firstMedia = mediaArray != null ? mediaArray.get(0).getAsJsonObject() : null;
        return firstMedia != null ?  firstMedia.get("media_url").getAsString() : null;
    }

}
