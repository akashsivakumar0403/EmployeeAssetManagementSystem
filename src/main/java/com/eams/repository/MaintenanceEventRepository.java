package com.eams.repository;
import com.eams.entity.MaintenanceEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceEventRepository extends JpaRepository<MaintenanceEvent, Long> {
}
