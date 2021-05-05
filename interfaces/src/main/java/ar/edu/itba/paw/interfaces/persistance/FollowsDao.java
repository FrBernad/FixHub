package ar.edu.itba.paw.interfaces.persistance;

public interface FollowsDao {

    void followUser(Long userId, Long userToFollowId);

    void unfollowUser(Long userId, Long userToFollowId);

}
