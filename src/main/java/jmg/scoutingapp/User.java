package jmg.scoutingapp;

import java.util.HashSet;
import java.util.Set;

public class User {

    private String username;
    private Set<User> following = new HashSet<>();
    private boolean isPrivate = false;
    private boolean isHidden = false;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void follow(User user) {
        following.add(user);
    }

    public void unfollow(User user) {
        following.remove(user);
    }

    public boolean isFollowing(User user) {
        return following.contains(user);
    }

    public boolean isMate(User user) {
        return isFollowing(user) && user.isFollowing(this);
    }

    public boolean isLeader(User user) {
        return isFollowing(user) && !user.isFollowing(this);
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        return username.equals(((User) obj).username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return username;
    }
}
