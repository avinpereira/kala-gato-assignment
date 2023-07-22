package com.kalagato.TollApplication.repository;

import com.kalagato.TollApplication.entity.Pass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassRepository extends JpaRepository<Pass, Long> {
}
