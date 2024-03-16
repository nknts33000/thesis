package com.example.platform.repo;

import com.example.platform.model.Post;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PostRepo extends JpaRepository<Post,Long> {
}
