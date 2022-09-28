package com.example.addressbook_application.service;

import com.example.addressbook_application.dto.AddressBookDTO;
import com.example.addressbook_application.exception.AddressBookException;
import com.example.addressbook_application.model.AddressBook;
import com.example.addressbook_application.repository.Repo;
import com.example.addressbook_application.utility.EmailSenderService;
import com.example.addressbook_application.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressBookService implements IAddressBookService {
    @Autowired
    Repo repository;
    @Autowired
    TokenUtility tokenUtility;
    @Autowired
    EmailSenderService emailSender;

    @Override
    public AddressBook saveData(AddressBookDTO addressBookData) {
        AddressBook addressBook = new AddressBook(addressBookData);
        return repository.save(addressBook);
    }
    @Override
    public Optional<AddressBook> findById(Long id) {
        Optional<AddressBook> getUserDetails = repository.findById(id);
        if (getUserDetails.isPresent()) {
            return getUserDetails;
        } else
            throw new AddressBookException("ID: "+id+", does not exist ");
    }
    @Override
    public List<AddressBook> findAllData() {
        return repository.findAll();
    }
    @Override
    public AddressBook editData(AddressBookDTO addressBookDTO, Long id) {
        AddressBook existingData = repository.findById(id).orElse(null);
        if (existingData != null) {
            existingData.setFullName(addressBookDTO.getFullName());
            existingData.setAddress(addressBookDTO.getAddress());
            existingData.setCity(addressBookDTO.getCity());
            existingData.setState(addressBookDTO.getState());
            existingData.setZip(addressBookDTO.getZip());
            existingData.setContactNumber(addressBookDTO.getContactNumber());
            existingData.setEmailAddress(addressBookDTO.getEmailAddress());
            //Email Body
            String userData = "UPDATED DETAILS: \n"+"Full Name: "+existingData.getFullName()+"\n"+"Address: "+existingData.getAddress()+"\n"
                    +"City: "+existingData.getCity()+"\n"+"State: "+existingData.getState()+"\n"+"Zip Code: "+existingData.getZip()+"\n"+
                    "Contact Number: "+existingData.getContactNumber()+"\n"+"Email Address: "+existingData.getEmailAddress();
            //sending email
            emailSender.sendEmail(addressBookDTO.getEmailAddress(),"Data Edited!!!", userData);

            return repository.save(existingData);
        } else
            throw new AddressBookException("Error: Cannot find the User Id " + id);
    }
    @Override
    public AddressBook deleteData(Long id) {
        AddressBook addressBookData = repository.findById(id).orElse(null);
        if(addressBookData != null){
            repository.deleteById(id);
            //sending email
            emailSender.sendEmail(addressBookData.getEmailAddress(), "Data Deleted!!!", "Your Data deleted successfully from the AddressBookSystem App!!");
        }else
            throw new AddressBookException("Error: Cannot find User ID " + id);
        return addressBookData;
    }
    @Override
    public List<AddressBook> getUserByEmail(String email) {
        List<AddressBook> existingData = repository.findUserByEmail(email);
        if(existingData.isEmpty()){
            throw new AddressBookException("No Data with Email Address: " + email);
        }else
            return existingData;
    }
    @Override
    public List<AddressBook> getUserByCity(String city) {
        List<AddressBook> existingData = repository.findUserByCity(city);
        if(existingData.isEmpty()){
            throw new AddressBookException("No Data with City: " + city);
        }else
            return existingData;
    }
    @Override
    public List<AddressBook> getUserByState(String state) {
        List<AddressBook> existingData = repository.findUserByState(state);
        if(existingData.isEmpty()){
            throw new AddressBookException("No Data with State: " + state);
        }else
            return existingData;
    }
    @Override
    public List<AddressBook> getUserByZip(String zip) {
        List<AddressBook> existingData = repository.findUserByZip(zip);
        if(existingData.isEmpty()){
            throw new AddressBookException("No Data with Zip Code: " + zip);
        }else
            return existingData;
    }
    @Override
    public String insertData(AddressBookDTO addressDto) throws AddressBookException {
        AddressBook addressBook =new AddressBook(addressDto);
        repository.save(addressBook);
        String token = tokenUtility.createToken(addressBook.getUserId());
        //email body
        String userData = "ADDED DETAILS: \n"+"Full Name: "+addressBook.getFullName()+"\n"+"Address: "+addressBook.getAddress()+"\n"
                +"City: "+addressBook.getCity()+"\n"+"State: "+addressBook.getState()+"\n"+"Zip Code: "+addressBook.getZip()+"\n"+
                "Contact Number: "+addressBook.getContactNumber()+"\n"+"Email Address: "+addressBook.getEmailAddress();
        //sending email
        emailSender.sendEmail(addressBook.getEmailAddress(),"Data Added!!!", userData);
        return token;
    }
    @Override
    public Optional<AddressBook> getUserDataByToken(String token) {
        Long Userid = tokenUtility.decodeToken(token);
        Optional<AddressBook> existingData = repository.findById(Userid);
        if(existingData.isPresent()){
            return existingData;
        }else
            throw new AddressBookException("Invalid Token");
    }
    @Override
    public List<AddressBook> getAllDataByToken(String token) {
        Long Userid = tokenUtility.decodeToken(token);
        Optional<AddressBook> existingData = repository.findById(Userid);
        if(existingData.isPresent()){
            List<AddressBook> existingAllData = repository.findAll();
            return existingAllData;
        }else
            throw new AddressBookException("Invalid Token");
    }

    @Override
    public String getTokenDetails(Long id) {
        AddressBook tokenDetails = repository.findById(id).orElse(null);
        if(tokenDetails != null){
            String token = tokenUtility.createToken(tokenDetails.getUserId());
            return token;
        }else
            throw new AddressBookException("Error: Cannot find User ID " + id);
    }
}