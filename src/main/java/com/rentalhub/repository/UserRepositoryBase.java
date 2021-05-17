package com.rentalhub.repository;

import com.rentalhub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepositoryBase<T extends User> extends JpaRepository<T, Long> {
    @Query("from #{#entityName} as t where t.login = :login ")
    Optional<T> findByLogin(String login);
}
