package com.socialmedia.repository;

import com.socialmedia.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;


public interface IAuthRepository extends JpaRepository<Auth,Long> {


    Optional<Auth> findOptionalByUsernameAndPassword(String username, String password);

    Boolean existsByUsername(String username);

    @Query("select a.username from Auth a where a.email= :email") //JPQL -->> Java(Jakarta) Persistence Query Language
    Optional<String> denemSorgusu(String email);
}
