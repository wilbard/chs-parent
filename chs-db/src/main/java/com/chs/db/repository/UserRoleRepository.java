package com.chs.db.repository;

import com.chs.db.model.Role;
import com.chs.db.model.User;
import com.chs.db.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findById(long id);
    List<UserRole> findAllByUser(User user);
    List<UserRole> findAllByRole(Role role);
    List<UserRole> findAll();
}
