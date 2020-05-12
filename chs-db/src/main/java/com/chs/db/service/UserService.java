package com.chs.db.service;

import com.chs.db.model.Role;
import com.chs.db.model.User;
import com.chs.db.repository.RoleRepository;
import com.chs.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User newUser(User user) {
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        return this.userRepository.save(user);
    }

    @Transactional
    public void addUser(User user) {
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        this.userRepository.save(user);
    }

    @Transactional
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Transactional
    public void removeUser(User user) {
        this.userRepository.delete(user);
    }

    @PostConstruct
    private void setupDefaultUser() {
        if (this.userRepository.count() == 0) {
            List<Role> roleList = new ArrayList<>();
            if (this.roleRepository.findByName("USER") == null) {
                roleList.add(new Role("USER"));
            }
            if (this.roleRepository.findByName("ADMIN") == null) {
                roleList.add(new Role("ADMIN"));
            }
            if (!roleList.isEmpty()) {
                this.roleRepository.saveAll(roleList);
            }

            String encodedPassword = new BCryptPasswordEncoder().encode("111111");
            User user = new User("wilbard", encodedPassword, Arrays.asList(this.roleRepository.findByName("USER"), this.roleRepository.findByName("ADMIN")));
            user.setFullName("Wilbard Shirima");
            user.setPhoneNumber("255752191969");
            user.setEnabled(true);
            user.setAccountNonLocked(true);
            user.setEmail("wilydammas@gmail.com");
            this.userRepository.save(user);
        }
    }

    public User findById(long id) {
        return this.userRepository.findById(id);
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public Role findUserRoleByRole(String name) {
        return this.roleRepository.findByName(name);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public List<User> findAll(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return this.userRepository.findAll();
        } else {
            return this.userRepository.findAllByFullNameLike(filterText);
        }
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> findAll(String filterText, Pageable pageable) {
        if (filterText == null || filterText.isEmpty()) {
            return this.userRepository.findAll(pageable);
        } else {
            return this.userRepository.findAllByFullNameLike(filterText, pageable);
        }
    }

    public Page<User> findAllPages(String filterText, int offset, int limit, Sort sort) {
        if (filterText == null || filterText.isEmpty()) {
            return this.userRepository.findAll(PageRequest.of(offset, limit, sort));
        } else {
            return this.userRepository.findAllByFullNameLike(filterText, PageRequest.of(offset, limit, sort));
        }
    }

    public int count(String filter) {
        if (filter == null || filter.isEmpty()) {
            return (int) userRepository.count();
        } else {
            return (int) userRepository.countByFullNameLike(filter);
        }
    }
}
