package com.example.questApp.dataAccess.abstracts;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.questApp.entities.concretes.Follower;
import com.example.questApp.entities.concretes.User;

public interface FollowerDao extends JpaRepository<Follower, Long> {
    // Changed to Optional<Follower> from List<Follower>
    Optional<Follower> findById(Long followerId);

    @Query("SELECT f FROM Follower f WHERE f.follower = :follower AND f.followed = :followed")
    Optional<Follower> findByFollowerAndFollowed(@Param("follower") User follower, @Param("followed") User followed);


    // Ensure that the method names match the query requirements
    List<Follower> findByFollower_Id(Long userId);

    List<Follower> findByFollowed_Id(Long userId);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
    	       "FROM Follower f WHERE f.follower.id = :userId AND f.followed.id = :flirtId")
    	boolean existsByUserIdAndFlirtId(@Param("userId") Long userId, @Param("flirtId") Long flirtId);

}
