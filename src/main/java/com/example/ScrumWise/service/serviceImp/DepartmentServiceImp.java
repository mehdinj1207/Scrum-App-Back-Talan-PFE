package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.model.entity.Department;
import com.example.ScrumWise.repository.DepartmentRepository;
import com.example.ScrumWise.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DepartmentServiceImp implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository ;
    @Override
    public Department getDepartmentById(Long id) {
        try{
            return departmentRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("Projet not found!");
            return new Department();
        }
    }

    @Override
    public List<Department> getDepartmentList() {
        return departmentRepository.findAll();
    }

    @Override
    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(Long id, Department department) {
        getDepartmentById(id);
        department.setIdDepartment(id);
        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
