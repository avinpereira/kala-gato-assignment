package com.kalagato.TollApplication.repository;

import com.kalagato.TollApplication.entity.Toll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TollRepository extends JpaRepository<Toll, Long> {
}
