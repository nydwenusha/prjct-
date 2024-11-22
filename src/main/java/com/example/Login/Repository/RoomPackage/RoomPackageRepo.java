package com.example.Login.Repository.RoomPackage;

import com.example.Login.Entity.RoomPackages.RoomPackages;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface RoomPackageRepo extends JpaRepository<RoomPackages, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM RoomPackages r WHERE r.pId = ?1")
    void  deleteRoom(Integer propertyId);
}
