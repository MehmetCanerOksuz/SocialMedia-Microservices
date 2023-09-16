package com.socialmedia.repository;

import com.socialmedia.repository.entity.Post;
import com.socialmedia.repository.enums.EStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends MongoRepository<Post,String > {


    Boolean existsByUsername(String username);

    Optional<Post> findByAuthId(Long authId);

    Optional<Post> findByUsername(String username);

    List<Post> findUserProfileByStatus(EStatus status);


    // MONGO DB QUERY YAZIMI...
    @Query("{username:  ?0, email:  ?1}")
    Optional<Post> getUser(String username, String email);

    @Query("{authId:  {$gt: ?0}}")
    Optional<Post> findUserGtId(Long authId);

    @Query("{$or:  [{authId: {$gt: ?0}},{status: ?1}]}")
    Optional<Post> getUserGtIdAndStatus(Long authId, EStatus Status);

    @Query("{status:  'ACTIVE'}")
    Optional<Post> findActiveProfile();
}
