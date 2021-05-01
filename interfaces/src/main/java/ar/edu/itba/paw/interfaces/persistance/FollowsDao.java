package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.User;

import java.util.Collection;

public interface FollowsDao {

    void followUser(Long userId, Long userToFollowId);

    void unfollowUser(Long userId, Long userToFollowId);

}
