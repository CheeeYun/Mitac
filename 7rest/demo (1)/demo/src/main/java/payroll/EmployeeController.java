package payroll;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;
    EmployeeController(EmployeeRepository repository,EmployeeModelAssembler assembler){
        this.repository=repository;
        this.assembler=assembler;
    }
//    @GetMapping("/employees")
//    CollectionModel<EntityModel> all(){
//        List<EntityModel> employees=repository.findAll().stream()
//                .map(employee->EntityModel.of(employee,
//                        linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
//                        linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
//                .collect(Collectors.toList());
//        return CollectionModel.of(employees,linkTo(methodOn(EmployeeController.class).all()).withSelfRel()
//                                            , linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
//    }
    @GetMapping("/employees/assembler")
    CollectionModel<EntityModel<Employee>>all(){
        List<EntityModel<Employee>> employees=repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(employees,linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @PostMapping("/employees")    //指定了請求本體的數據映射到這個叫newEmployee的物件
    ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee){   //將newEmployee儲存並轉成EntityModel
        EntityModel<Employee> entityModel=assembler.toModel(repository.save(newEmployee));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);     //將包含newEmployee的entityModel作為響應本體返回
                                        //創建一個created 201響應並利用entityModel的連結取得uri

    }

//    @GetMapping("/employees/{id}")
//    public EntityModel<Employee>one(@PathVariable Long id){
//        Employee employee=repository.findById(id)
//                .orElseThrow(()->new EmployeeNotFoundException(id));
//        WebMvcLinkBuilder linkself = linkTo(methodOn(EmployeeController.class).one(employee.getId()));
//        EntityModel<Employee> model = EntityModel.of(employee,linkself.withRel("self"));
//        Link link1 = linkTo(EmployeeController.class).slash("employee").slash("2").withRel("l1");
//        Link link2 = linkTo(EmployeeController.class).slash("employee").slash("3").withRel("l2");
//        System.out.println(link2);
//        model.add(link1);
//        model.add(link2);
//        return model;
//        return EntityModel.of(employee,
//                likTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
//                likTo(methodOn(EmployeeController.class).all()).withRel("employees"));
//    }
    @GetMapping("/employees/assembler/{id}")    //用id找單個employee
    EntityModel<Employee> one(@PathVariable Long id){
        Employee singleEmployee= repository.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException(id));
        return assembler.toModel(singleEmployee);
    }
    @PutMapping("/employees/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody Employee newEmployee,@PathVariable Long id){
        Employee updatedEmployee= repository.findById(id)
                .map(employee->{
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(()->{
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
        });
        EntityModel<Employee> entityModel=assembler.toModel(updatedEmployee);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build(); //返回一個204 沒有內文
    }

}
