package com.restapi.sample.employee;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class EmployeeController {

    private final EmployeeRepository repository;

    private final EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/employees")
    @Operation(summary = "모든 직원 조회")
    public CollectionModel<EntityModel<Employee>> all() {

        final var employees = repository.findAll()
                                        .stream()
                                        .map(assembler::toModel)
                                        .collect(Collectors.toList());

        return CollectionModel.of(employees,
                                    linkTo(methodOn(EmployeeController.class).all()).withSelfRel());

    }
    // end::get-aggregate-root[]



    @PostMapping("employees")
    @Operation(summary = "새로운 직원 등록")
    ResponseEntity<?> newEmployee(@RequestBody Employee employee) {

        EntityModel<Employee> entityModel = assembler.toModel(repository.save(employee));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }



    @GetMapping("/employee/{id}")
    @Operation(summary = "직원 아이디로 직원 조회")
    public EntityModel<Employee> one(@PathVariable Long id) {

        final var employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

        return assembler.toModel(employee);
    }




    @PutMapping("/employees/{id}")
    @Operation(summary = "직원 정보 업데이트")
    ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

        Employee updatedEmployee = repository.findById(id)
                .map(employee -> {
                   employee.setName(newEmployee.getName());
                   employee.setRole(newEmployee.getRole());
                   return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });

        EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }



    @DeleteMapping("/employees/{id}")
    @Operation(summary = "직원 삭제")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
