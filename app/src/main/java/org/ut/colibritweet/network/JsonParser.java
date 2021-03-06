package org.ut.colibritweet.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ut.colibritweet.pojo.Tweet;
import org.ut.colibritweet.pojo.User;



import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Collection;

public class JsonParser {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Tweet.class, new TweetDeserializer())
            .create();

    public Collection<User> getUsers(String response) {
        Type usersType = new TypeToken<Collection<User>>(){}.getType();
        return GSON.fromJson(response, usersType);
    }

    public Collection<Tweet> getTweets(String response) {
        Type tweetsType = new TypeToken<Collection<Tweet>>(){}.getType();
        return GSON.fromJson(response, tweetsType);
    }

    public User getUser(String response) {
        return GSON.fromJson(response, User.class);
    }

  /*  public Collection<Tweet> getTweets(String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        Collection<Tweet> tweetsResult = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = jsonArray.getJSONObject(i);
            long id = tweetJson.getLong("id");
            String creationDate = tweetJson.getString("created_at");
            String fullText = tweetJson.getString("full_text");
            long retweetCount = tweetJson.getLong("retweet_count");
            long likesCount = tweetJson.getLong("favorite_count");

            String imageUrl = getTweetImageUrl(tweetJson);

            JSONObject userJson = tweetJson.getJSONObject("user");
            User user = getUser(userJson.toString());

            Tweet tweet = new Tweet(user, id, creationDate, fullText, retweetCount, likesCount, imageUrl);
            tweetsResult.add(tweet);
        }

        return tweetsResult;
    }*/

  /*  private String getTweetImageUrl(JSONObject tweetJson) throws JSONException {
        JSONObject entities = tweetJson.getJSONObject("entities");
        JSONArray mediaArray = entities.has("media") ? entities.getJSONArray("media") : null;
        JSONObject firstMedia = mediaArray != null ? mediaArray.getJSONObject(0) : null;
        return firstMedia != null ?  firstMedia.getString("media_url") : null;
    }*/
}
