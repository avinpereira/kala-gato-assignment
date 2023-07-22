package com.kalagato.TollApplication.repository;

import com.kalagato.TollApplication.entity.TollBooth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TollBoothRepository extends JpaRepository<TollBooth, Long> {
}
