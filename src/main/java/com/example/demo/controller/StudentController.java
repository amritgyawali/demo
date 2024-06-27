package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.request.CreateStudentRequest;
import com.example.demo.request.InQueryRequest;
import com.example.demo.request.UpdateStudentRequest;
import com.example.demo.response.StudentResponse;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;


    @GetMapping("/getall")
   public List<StudentResponse> getAllStudents(){

        logger.error("inside error");
        logger.warn("inside warn");
        logger.info("inside info");
        logger.debug("inside debug");
        logger.trace("inside trace");
        List<Student> studentList = studentService.getALLStudents();
         List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();

        studentList.stream().forEach(student -> {
            studentResponseList.add(new StudentResponse(student));
        });
        return studentResponseList;
    }

    @PostMapping("/create")
    public StudentResponse createStudent(@Valid @RequestBody CreateStudentRequest createStudentRequest){
        Student student = studentService.createStudent(createStudentRequest);
        return new StudentResponse(student);
    }

    @PutMapping("/update")
    public StudentResponse updateStudent(@Valid @RequestBody UpdateStudentRequest updateStudentRequest){
        Student student = studentService.updateStudent(updateStudentRequest);
        return new StudentResponse(student);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable long id){
        return studentService.deleteStudent(id);
    }

    @GetMapping("/getbyfirstname/{firstName}")
    public List<StudentResponse> getByFirstName(@PathVariable String firstName){
        List<Student> studentList = studentService.getByFirstName(firstName);
        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();

        studentList.stream().forEach(student -> {
            studentResponseList.add(new StudentResponse(student));
        });
        return studentResponseList;
    }

    @GetMapping("/getbyfirstnameorlastname/{firstName}/{lastName}")
    public List<StudentResponse> getByFirstNameOrLastName(@PathVariable String firstName , @PathVariable String lastName){
        List<Student> studentList = studentService.getByFirstNameOrLastName(firstName, lastName);
        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();

        studentList.stream().forEach(student -> {
            studentResponseList.add(new StudentResponse(student));
        });
        return studentResponseList;
    }

    @GetMapping("/getbyfirstnamein")
    public List<StudentResponse> getByFirstNameIn(@RequestBody InQueryRequest inQueryRequest){
        logger.info("inQueryRequest====" + inQueryRequest);
        List<Student> studentList = studentService.getByFirstNameIn(inQueryRequest);
        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();
        studentList.stream().forEach(student -> {
            studentResponseList.add(new StudentResponse(student));
        });
        logger.info("studentresponselist====" + studentResponseList);

        return studentResponseList;
    }

    @GetMapping("/getallwithpagination")
    public List<StudentResponse> getAllStudentsWithPagination(@RequestParam int pageNo, @RequestParam int pageSize){
        List<Student> studentList = studentService.getAllStudentsWithPagination(pageNo,pageSize);
        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();
        studentList.stream().forEach(student -> {
            studentResponseList.add((new StudentResponse(student)));
        });
        return studentResponseList;
    }

    @GetMapping("/getallwithsorting")
    public List<StudentResponse> getAllStudentsWithSorting(){
        List<Student> studentList = studentService.getAllStudentsWithSorting();
        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();
        studentList.stream().forEach(student -> {
            studentResponseList.add((new StudentResponse(student)));
        });
        return studentResponseList;
    }

    @GetMapping("/like/{firstName}")
    public List<StudentResponse> like(@PathVariable String firstName){
        List<Student> studentList = studentService.like(firstName);
        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();
        studentList.stream().forEach(student -> {
            studentResponseList.add((new StudentResponse(student)));
        });
        return studentResponseList;
    }

    @PutMapping("/updateFirstName/{id}/{firstName}")
    public String updateStudentWithJpql(@PathVariable Long id, @PathVariable String firstName){
        return studentService.updateStudentWithJpql(id,firstName)+ "Student(s) updated";
    }

    @GetMapping("/getByCity/{city}")
    public List<StudentResponse> getByCity(@PathVariable String city){
        List<Student> studentList = studentService.getByCity(city);

        List<StudentResponse> studentResponseList = new ArrayList<StudentResponse>();

        studentList.stream().forEach(student -> {
            studentResponseList.add(new StudentResponse(student));
        });
    return studentResponseList;
    }


}
