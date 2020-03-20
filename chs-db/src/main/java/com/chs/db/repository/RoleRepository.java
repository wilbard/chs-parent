package com.chs.db.repository;

import com.chs.db.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findById(long id);
    Role findByName(String name);

    List<Role> findAll();
}
