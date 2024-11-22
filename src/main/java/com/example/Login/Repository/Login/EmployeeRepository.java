package com.example.Login.Repository.Login;

import com.example.Login.Entity.Login.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Transactional
    @Modifying
    @Query("update Employee u set u.password = ?2 where u.user_id = ?1")
    void updatePassword(String email, String password);


}
