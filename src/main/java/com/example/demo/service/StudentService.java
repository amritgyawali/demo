package com.example.demo.service;



import com.example.demo.entity.Address;
import com.example.demo.entity.Student;
import com.example.demo.entity.Subject;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.SubjectRepository;
import com.example.demo.request.CreateStudentRequest;
import com.example.demo.request.CreateSubjectRequest;
import com.example.demo.request.InQueryRequest;
import com.example.demo.request.UpdateStudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    SubjectRepository subjectRepository;

    public List<Student> getALLStudents() {
      return studentRepository.findAll();
    }

    public Student createStudent(CreateStudentRequest createStudentRequest){
        Student student = new Student(createStudentRequest);
        Address address = new Address();
        address.setStreet(createStudentRequest.getStreet());
        address.setCity(createStudentRequest.getCity());

        address = addressRepository.save(address);
        student.setAddress(address);

        student = studentRepository.save(student);

        List<Subject> subjectsList = new ArrayList<Subject>();

        if(createStudentRequest.getSubjectsLearning()!=null){
            for (CreateSubjectRequest createSubjectRequest : createStudentRequest.getSubjectsLearning()){
                Subject subject = new Subject();
                subject.setSubjectName(createSubjectRequest.getSubjectName());
                subject.setMarksObtained(createSubjectRequest.getMarksObtained());
                subject.setStudent(student);
                subjectsList.add(subject);
            }
            subjectRepository.saveAll(subjectsList);
        }
        student.setLearningSubjects(subjectsList);
        return student;
    }

    public Student updateStudent(UpdateStudentRequest updateStudentRequest){
        Student student = studentRepository.findById(updateStudentRequest.getId()).get();
        if (updateStudentRequest.getFirstName()!=null &&
            !updateStudentRequest.getFirstName().isEmpty()){
            student.setFirstName(updateStudentRequest.getFirstName());
        }
        student = studentRepository.save(student);
        return student;
    }

    public String deleteStudent(long id){
        studentRepository.deleteById(id);
        return "Student data deleted successfully";
    }

    public List<Student> getByFirstName(String firstName){
        return studentRepository.findByFirstName(firstName);
    }

    public List<Student> getByFirstNameOrLastName(String firstName , String lastName){
        return studentRepository.getByFirstNameOrLastName(firstName,lastName);
    }

    public List<Student> getByFirstNameIn (InQueryRequest inQueryRequest) {
        return studentRepository.findByFirstNameIn(inQueryRequest.getFirstNames());
    }

    public List<Student> getAllStudentsWithPagination(int pageNo,int pageSize){
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        return studentRepository.findAll(pageable).getContent();
    }

    public List<Student> getAllStudentsWithSorting(){
        Sort sort = Sort.by(Sort.Direction.ASC, "firstName");
        return studentRepository.findAll(sort);
    }

    public List<Student> like(String firstName){
        return studentRepository.findByFirstNameContains(firstName);
    }

    public Integer updateStudentWithJpql(Long id , String firstName){
        return studentRepository.updateFirstName(id,firstName);
    }

//    public List<Student> getByCity(String city){
//        return studentRepository.findByAddressCity(city);
//    }
public List<Student> getByCity(String city){
    return studentRepository.getByAddressCity(city);
}
}
