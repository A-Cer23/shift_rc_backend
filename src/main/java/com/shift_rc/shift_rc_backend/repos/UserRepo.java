package com.shift_rc.shift_rc_backend.repos;

import com.shift_rc.shift_rc_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("UPDATE User u SET u.firstName=:firstName, u.lastName=:lastName WHERE u.id=:id")
    public void updateUserInfo(@Param("id") Long id, @Param("firstName") String firstName, @Param("lastName") String lastName);


    @Query("SELECT u FROM User u WHERE u.email=:email AND u.password=:password ")
    public User loginUser(@Param("email") String email, @Param("password") String password);
}
