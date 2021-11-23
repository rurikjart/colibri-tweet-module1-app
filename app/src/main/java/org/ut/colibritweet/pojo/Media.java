package org.ut.colibritweet.pojo;

import java.util.Objects;

public class Media {

    private String media;

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media1 = (Media) o;
        return Objects.equals(media, media1.media);
    }

    @Override
    public int hashCode() {
        return Objects.hash(media);
    }

    @Override
    public String toString() {
        return "Media{" +
                "media='" + media + '\'' +
                '}';
    }
}
