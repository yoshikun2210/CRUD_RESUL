package nam.tran.controller;

import nam.tran.dto.response.ResponseMessage;
import nam.tran.model.Student;
import nam.tran.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        if (student.getName().trim().equals("") || student.getName().length() < 3 || student.getName().length() > 10) {
            return new ResponseEntity<>(new ResponseMessage("The name invalid"), HttpStatus.OK);
        }
        if (studentService.existsByName(student.getName())) {
            return new ResponseEntity<>(new ResponseMessage("The name is existed"), HttpStatus.OK);
        }
        studentService.save(student);
        return new ResponseEntity<>(new ResponseMessage("Create success"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> showListStudent() {
        return new ResponseEntity<>(studentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> pageStudent(@PageableDefault(sort = "name", size = 2) Pageable pageable) {
        return new ResponseEntity<>(studentService.findAll(pageable), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Optional<Student> student1 = studentService.findById(id);
        if (!student1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (student.getName().trim().equals("") || student.getName().length() < 3 || student.getName().length() > 10) {
            return new ResponseEntity<>(new ResponseMessage("The name invalid"), HttpStatus.OK);
        }
        if (studentService.existsByName(student.getName())) {
            return new ResponseEntity<>(new ResponseMessage("The name is existed"), HttpStatus.OK);
        }
        student1.get().setName(student.getName());
        studentService.save(student1.get());
        return new ResponseEntity<>(new ResponseMessage("Update success"), HttpStatus.OK);
    }

    // CHI TIET
    @GetMapping("{id}")
    public ResponseEntity<?> detailStudent(@PathVariable Long id) {
        Optional<Student> student = studentService.findById(id);
        if (!student.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student.get(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        Optional<Student> student = studentService.findById(id);
        if (!student.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("Delete success !"), HttpStatus.OK);
    }
    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchByName(@PathVariable String name){
        return new ResponseEntity<>(studentService.findAllByNameContaining(name),HttpStatus.OK);
    }
}
