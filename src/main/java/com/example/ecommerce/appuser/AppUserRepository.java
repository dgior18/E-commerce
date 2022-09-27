package com.example.ecommerce.appuser;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository
        extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByIdNumber(Long idNumber);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    void enableAppUser(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.cashAmount = ?1 WHERE a.email = ?2")
    void updateCash(double cashAmount, String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.password = ?1 WHERE a.idNumber = ?2")
    void updatePassword(String password, Long idNumber);

}
