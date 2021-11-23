package org.ut.colibritweet.pojo;

import java.util.Objects;

public class Tweet {
    private User user;
    private Long id;
    private String creationDate;
    private String text;
    private Long retweetCount;
    private Long favouriteCount;
    private Entities entities;
    private String imageUrl;


    public Tweet(User user, Long id, String creationDate, String text, Long retweetCount, Long favouriteCount, Entities entities, String imageUrl) {
        this.user = user;
        this.id = id;
        this.creationDate = creationDate;
        this.text = text;
        this.retweetCount = retweetCount;
        this.favouriteCount = favouriteCount;
        this.entities = entities;
        this.imageUrl = imageUrl;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getText() {
        return text;
    }

    public Long getRetweetCount() {
        return retweetCount;
    }

    public Long getFavouriteCount() {
        return favouriteCount;
    }

    public Entities getEntities() {
        return entities;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return Objects.equals(user, tweet.user) && Objects.equals(id, tweet.id) && Objects.equals(creationDate, tweet.creationDate) && Objects.equals(text, tweet.text) && Objects.equals(retweetCount, tweet.retweetCount) && Objects.equals(favouriteCount, tweet.favouriteCount) && Objects.equals(entities, tweet.entities) && Objects.equals(imageUrl, tweet.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, id, creationDate, text, retweetCount, favouriteCount, entities, imageUrl);
    }
}