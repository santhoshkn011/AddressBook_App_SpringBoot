package com.example.addressbook_application.repository;

import com.example.addressbook_application.model.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Repo extends JpaRepository<AddressBook, Long> {
//    @Query(value="SELECT * FROM address_book, email_address WHERE user_id = id AND email = :email", nativeQuery=true)
//    List<AddressBook> findUserByEmail(String email);
    @Query(value="SELECT * FROM address_book, cities WHERE user_id = id AND city = :city", nativeQuery=true)
    List<AddressBook> findUserByCity(String city);
    @Query(value="SELECT * FROM address_book WHERE user_id = user_id AND state = :state", nativeQuery=true)
    List<AddressBook> findUserByState(String state);
    @Query(value="SELECT * FROM address_book WHERE user_id = user_id AND zip = :zip", nativeQuery=true)
    List<AddressBook> findUserByZip(String zip);
    @Query(value="SELECT * FROM address_book WHERE user_id = user_id AND email = :email", nativeQuery=true)
    List<AddressBook> findUserByEmail(String email);
}