package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.Department;

import java.util.List;

public interface DepartmentService {
    Department getDepartmentById(Long id);
    List<Department> getDepartmentList();
    Department addDepartment(Department department);
    Department updateDepartment(Long id, Department department);
    void deleteDepartment(Long id);
}
