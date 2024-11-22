package com.example.Login.Repository.Driver;

import com.example.Login.Entity.Driver.DriverRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface DriverRepo extends JpaRepository<DriverRegistration, String> {

}
