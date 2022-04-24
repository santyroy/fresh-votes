package com.freshvotes.domain;


import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CommentId implements Serializable {

    @ManyToOne
    private User user;
    @ManyToOne
    private Feature feature;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentId commentId = (CommentId) o;
        return Objects.equals(user, commentId.user) && Objects.equals(feature, commentId.feature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, feature);
    }
}
