package com.example.ScrumWise.repository;
import com.example.ScrumWise.model.entity.Department;
import com.example.ScrumWise.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findUserByStatus(boolean status);
    User findByEmail(String email);

    List<User> findUsersByDepartment(Department department);
    List<User> findByEmailIn(List<String> emails);
}
