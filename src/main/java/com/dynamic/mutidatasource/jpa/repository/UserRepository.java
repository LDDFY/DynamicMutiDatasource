package com.dynamic.mutidatasource.jpa.repository;

import com.dynamic.mutidatasource.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findById(int id);

    public User saveAndFlush(User user);
}
