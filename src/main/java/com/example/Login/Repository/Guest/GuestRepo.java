package com.example.Login.Repository.Guest;

import com.example.Login.Entity.Login.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface GuestRepo extends JpaRepository<Guest,String> {


}
