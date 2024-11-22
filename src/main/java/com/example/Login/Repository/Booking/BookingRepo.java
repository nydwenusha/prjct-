package com.example.Login.Repository.Booking;

import com.example.Login.Entity.Booking.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface BookingRepo extends JpaRepository<BookingEntity, String> {

}
