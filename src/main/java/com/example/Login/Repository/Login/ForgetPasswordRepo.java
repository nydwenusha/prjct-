package com.example.Login.Repository.Login;

import com.example.Login.Entity.Login.Employee;
import com.example.Login.Entity.Login.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgetPasswordRepo extends JpaRepository<ForgotPassword, Integer> {

    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and fp.employee = ?2")
    Optional<ForgotPassword> findByOtpAndEmployee(Integer otp, Employee employee);

    @Query("select fp from ForgotPassword fp where fp.employee = ?1")
    Optional<ForgotPassword> findByEmployee(Employee employee);

    //void deleteByExpirationTimeBefore(Date expirationTime);
}
