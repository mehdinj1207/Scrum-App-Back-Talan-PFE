package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.model.entity.Department;
import com.example.ScrumWise.rest.dto.DepartmentDto;
import com.example.ScrumWise.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.lang.reflect.Type;
import java.util.List;
@RestController
@CrossOrigin
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService ;
    @Autowired
    private ModelMapper modelMapper ;
    @GetMapping("/departments")
    @RolesAllowed({"Human Ressources","Manager","Consultant"})
    public Object DepartmentList() {
        List<Department> departments = departmentService.getDepartmentList();
        Type listType = new TypeToken<List<DepartmentDto>>() {}.getType() ;
        List<DepartmentDto> departmentDtos = modelMapper.map(departments,listType);
        return ResponseEntity.status(HttpStatus.OK).body(departmentDtos);
    }
    @GetMapping("/departments/{idDepartment}")
    @RolesAllowed({"Human Ressources","Manager","Consultant"})
    public Object Department(@PathVariable Long idDepartment ) {
        Department department = departmentService.getDepartmentById(idDepartment) ;
        if (department.getIdDepartment()==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            DepartmentDto departmentDto = modelMapper.map(department, DepartmentDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(departmentDto);
        }
    }

    @PostMapping("/departments")
    @RolesAllowed("Human Ressources")
    public Object AddDepartment(@Validated @RequestBody DepartmentDto departmentDto)
    {
        Department department = modelMapper.map(departmentDto, Department.class);
        department = departmentService.addDepartment(department);
        departmentDto = modelMapper.map(department, DepartmentDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentDto);
    }

    @PutMapping("/departments/{idDepartment}")
    @RolesAllowed("Human Ressources")
    public Object UpdateDepartment (@Validated @RequestBody DepartmentDto departmentDto, @PathVariable Long idDepartment) {
        Department department = modelMapper.map(departmentDto, Department.class);
        department = departmentService.updateDepartment(idDepartment, department);
        departmentDto = modelMapper.map(department, DepartmentDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentDto);

    }

    @DeleteMapping("/departments/{idDepartment}")
    @RolesAllowed("Human Ressources")
    public Object DeleteDepartment(@PathVariable Long idDepartment) {
        departmentService.deleteDepartment(idDepartment);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
