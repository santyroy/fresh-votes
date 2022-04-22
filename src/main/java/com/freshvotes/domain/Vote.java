package com.freshvotes.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Vote {

    @EmbeddedId
    private VoteId pk;
    private boolean upvote;

    public VoteId getPk() {
        return pk;
    }

    public void setPk(VoteId pk) {
        this.pk = pk;
    }

    public boolean isUpvote() {
        return upvote;
    }

    public void setUpvote(boolean upvote) {
        this.upvote = upvote;
    }
}
