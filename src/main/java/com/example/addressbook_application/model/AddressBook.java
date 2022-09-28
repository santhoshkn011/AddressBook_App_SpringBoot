package com.example.addressbook_application.model;

import com.example.addressbook_application.dto.AddressBookDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "Address_Book")
public @Data class AddressBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId")
    Long userId;
    @Column(name = "Full_Name")
    String fullName;
    String address;
    @ElementCollection
    @CollectionTable(name = "Cities", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "city")
    List<String> city;
    String state;
    String zip;
    String contactNumber;
//    @ElementCollection
//    @CollectionTable(name = "Email_Address", joinColumns = @JoinColumn(name = "id"))
//    @Column(name = "email")
    String emailAddress;

    public AddressBook(AddressBookDTO addressBookData) {
//        this.userId = addressBookData.getUserId();
        this.fullName = addressBookData.getFullName();
        this.address = addressBookData.getAddress();
        this.city = addressBookData.getCity();
        this.state = addressBookData.getState();
        this.zip = addressBookData.getZip();
        this.contactNumber = addressBookData.getContactNumber();
        this.emailAddress = addressBookData.getEmailAddress();
    }
}