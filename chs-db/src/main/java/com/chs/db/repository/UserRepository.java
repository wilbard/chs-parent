package com.chs.db.repository;

import com.chs.db.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);
    User findByUsername(String username);

    @Query(value = "SELECT COUNT(*) FROM user u WHERE u.full_name LIKE %?1%", nativeQuery = true)
    long countByFullNameLike(String filterText);


    List<User> findAll();

    @Override
    Page<User> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM user u WHERE u.full_name LIKE %?1%", nativeQuery = true)
    List<User> findAllByFullNameLike(String filterText);

    @Query(value = "SELECT * FROM user u WHERE u.full_name LIKE %?1%", countQuery = "SELECT COUNT(*) FROM user u WHERE u.full_name LIKE %?1%", nativeQuery = true)
    Page<User> findAllByFullNameLike(String filterText, Pageable pageable);
}
