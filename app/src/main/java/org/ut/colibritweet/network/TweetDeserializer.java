package org.ut.colibritweet.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ut.colibritweet.pojo.Tweet;
import org.ut.colibritweet.pojo.User;

import java.lang.reflect.Type;

public class TweetDeserializer implements JsonDeserializer {


    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {



        JsonObject tweetJson = json.getAsJsonObject();
        long id = tweetJson.get("id").getAsLong();
        String creationDate = tweetJson.get("created_at").getAsString();
        String fullText = tweetJson.get("full_text").getAsString();
        long retweetCount = tweetJson.get("retweet_count").getAsLong();
        long likesCount = tweetJson.get("favorite_count").getAsLong();

        String imageUrl = getTweetImageUrl(tweetJson);

        JsonObject userJson = tweetJson.get("user").getAsJsonObject();
        User user = context.deserialize(userJson, User.class);

        Tweet tweet = new Tweet(user, id, creationDate, fullText, retweetCount, likesCount, imageUrl);

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
