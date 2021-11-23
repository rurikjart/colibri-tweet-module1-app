package org.ut.colibritweet.pojo;

import java.util.Objects;

public class Entities {

    private Media media;

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entities entities = (Entities) o;
        return Objects.equals(media, entities.media);
    }

    @Override
    public int hashCode() {
        return Objects.hash(media);
    }

    @Override
    public String toString() {
        return "Entities{" +
                "media=" + media +
                '}';
    }
}
