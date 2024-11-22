package com.example.Login.Repository.Property.Room;

import com.example.Login.Entity.Property.Room.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@EnableJpaRepositories
@Repository
public interface RoomRepo extends CrudRepository<Room, String> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Room r WHERE r.propertyId = ?1")
    void  deleteRoom(String propertyId);


}
