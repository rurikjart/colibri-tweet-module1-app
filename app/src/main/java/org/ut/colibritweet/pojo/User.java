package org.ut.colibritweet.pojo;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id") private long id;
    @SerializedName("profile_image_url") private String imageUrl;
    @SerializedName("name") private String name;
    @SerializedName("screen_name") private String nick;
    @SerializedName("description") private String description;
    @SerializedName("location") private String location;
    @SerializedName("favourites_count") private int followingCount;
    @SerializedName("followers_count") private int followersCount;

    // удаляем конструктор он не вызывается неявно библиотекой гсон, и явная реализация нам не нужна

    public long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getNick() {
        return "@" + nick;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (followingCount != user.followingCount) return false;
        if (followersCount != user.followersCount) return false;
        if (imageUrl != null ? !imageUrl.equals(user.imageUrl) : user.imageUrl != null)
            return false;
        if (!name.equals(user.name)) return false;
        if (!nick.equals(user.nick)) return false;
        if (description != null ? !description.equals(user.description) : user.description != null) {
            return false;
        }
        return location != null ? location.equals(user.location) : user.location == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + nick.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + followingCount;
        result = 31 * result + followersCount;
        return result;
    }
}