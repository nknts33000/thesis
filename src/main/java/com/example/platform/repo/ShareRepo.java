package com.example.platform.repo;

import com.example.platform.model.Share;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ShareRepo extends JpaRepository<Share,Long> {
}
