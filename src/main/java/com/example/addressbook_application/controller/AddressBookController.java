package com.example.addressbook_application.controller;

import com.example.addressbook_application.dto.AddressBookDTO;
import com.example.addressbook_application.dto.ResponseDTO;
import com.example.addressbook_application.model.AddressBook;
import com.example.addressbook_application.service.IAddressBookService;
import com.example.addressbook_application.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class AddressBookController {
    @Autowired
    IAddressBookService service;
    @Autowired
    TokenUtility tokenUtility;
    //welcome message
    @RequestMapping(value = {"", "/", "/home"}, method = RequestMethod.GET)
    public String greet() {
        return "Hello! This is Address Book Home Page";
    }
    //Adding data
    @PostMapping("/post")
    public ResponseEntity<ResponseDTO> addUserDataById(@Valid @RequestBody AddressBookDTO addressBookData) {
        AddressBook response = service.saveData(addressBookData);
        ResponseDTO responseDTO = new ResponseDTO("Data Added Successfully", Optional.ofNullable(response));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //get data by id
    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseDTO> getUserData(@PathVariable Long id) {
        Optional<AddressBook> addressBookData = service.findById(id);
        ResponseDTO respDTO= new ResponseDTO("User Data with ID: " + id, addressBookData);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //get all the data
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> findAllData() {
        List<AddressBook> userDataList = service.findAllData();
        ResponseDTO respDTO = new ResponseDTO("All User Details Data, Total count: "+ userDataList.size(), userDataList);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //Edit or Update the data by id
    @PutMapping("/edit/{id}")
    public ResponseEntity<ResponseDTO> updateUserData(@PathVariable Long id,@Valid @RequestBody AddressBookDTO addressBookDTO) {
        Optional<AddressBook> userData = Optional.ofNullable(service.editData(addressBookDTO, id));
        ResponseDTO respDTO= new ResponseDTO("Data Update info", userData);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //Delete the data by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity <ResponseDTO> deleteUserData(@PathVariable Long id) {
        service.deleteData(id);
        ResponseDTO respDTO= new ResponseDTO("Deleted Successfully and e-mail sent", "Deleted User ID: " + id);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //find the User details by email address
    @GetMapping("/email/{email}")
    public ResponseEntity <ResponseDTO> getUserByEmail(@PathVariable String email) {
        List<AddressBook> userDataList = service.getUserByEmail(email);
        ResponseDTO respDTO = new ResponseDTO("User Data with Email Address: " + email +", Total count: "+ userDataList.size(), userDataList);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //Find the User details by City
    @GetMapping("/city/{city}")
    public ResponseEntity <ResponseDTO> getUserByCity(@PathVariable String city) {
        List<AddressBook> userDataList = service.getUserByCity(city);
        ResponseDTO respDTO = new ResponseDTO("User Data with City: " + city+", Total count: "+ userDataList.size(), userDataList);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //Find the User details by State
    @GetMapping("/state/{state}")
    public ResponseEntity <ResponseDTO> getUserByState(@PathVariable String state) {
        List<AddressBook> userDataList = service.getUserByState(state);
        ResponseDTO respDTO = new ResponseDTO("User Data with State: " + state+", Total count: "+ userDataList.size(), userDataList);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //Find the User details by Zip Code
    @GetMapping("/zip/{zip}")
    public ResponseEntity <ResponseDTO> getUserByZip(@PathVariable String zip) {
        List<AddressBook> userDataList = service.getUserByZip(zip);
        ResponseDTO respDTO = new ResponseDTO("User Data with Zip Code: " + zip+", Total count: "+ userDataList.size(), userDataList);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //insert data using Utility layer and generated Token
    @PostMapping("/insert")
    public ResponseEntity<String>AddAddressDetails(@Valid @RequestBody AddressBookDTO addressDto) {
        String token = service.insertData(addressDto);
        ResponseDTO respDTO = new ResponseDTO("Data Added Successfully and email sent to the User", token);
        return new ResponseEntity(respDTO, HttpStatus.CREATED);
    }
    //retrieve the User data with token value
    @GetMapping("/getUser/{token}")
    public ResponseEntity<String>getUserDetails(@PathVariable String token){
        Optional<AddressBook> userData = service.getUserDataByToken(token);
        Long Userid = tokenUtility.decodeToken(token);
        ResponseDTO respDTO = new ResponseDTO("Data retrieved successfully for the ID: "+Userid, userData);
        return new ResponseEntity(respDTO, HttpStatus.OK);
    }
    //Get all the data with any Token
    @GetMapping("/getAll/{token}")
    public ResponseEntity<String>getAllData(@PathVariable String token){
        List<AddressBook> userData = service.getAllDataByToken(token);
        ResponseDTO respDTO = new ResponseDTO("Data retrieved successfully, Total count: "+ userData.size(), userData);
        return new ResponseEntity(respDTO, HttpStatus.OK);
    }
    //get encrypted token details by id
    @GetMapping("/getToken/{id}")
    public ResponseEntity<String>getToken(@PathVariable Long id){
        String token = service.getTokenDetails(id);
        ResponseDTO respDTO = new ResponseDTO("Encrypted token for the ID: " + id, token);
        return new ResponseEntity(respDTO, HttpStatus.OK);
    }
}