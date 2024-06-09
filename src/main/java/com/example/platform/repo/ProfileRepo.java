package com.example.platform.repo;

import com.example.platform.model.Profile;
import com.example.platform.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface ProfileRepo extends JpaRepository<Profile,Long> {

    @Query("delete Profile p where p.user=?1")
    void deleteProfileByUser(User user);

    @Query("update Profile p set p.profilePicture=?1 where p.profile_id=?2")
    @Modifying
    void updateProfPic(byte[] fileBytes, long profile_id);


    Optional<Profile> findByUser(User user);
}
