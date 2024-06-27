package com.example.demo.repository;

import com.example.demo.entity.Address;
import com.example.demo.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findByFirstName(String firstName);
//    List<Student> findByFirstNameOrLastName(String firstName,String lastName);
    List<Student> findByFirstNameIn(List<String> firstNames);
    List<Student> findByFirstNameContains(String firstName);

    @Query(" From Student where firstName = :firstName and lastName = :lastName")
    List<Student> getByFirstNameOrLastName(String firstName,String lastName);

    @Modifying
    @Transactional
    @Query("Update Student set firstName = :firstName where id = :id")
    Integer updateFirstName(Long id,String firstName);


    List<Student> findByAddressCity(String city);

    @Query("From Student where address.city = :city")
    List<Student> getByAddressCity(String city);
}
