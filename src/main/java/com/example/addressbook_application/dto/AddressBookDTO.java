package com.example.addressbook_application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@NoArgsConstructor
public @ToString class AddressBookDTO {
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message="Invalid Name(First Letter Should be in Upper Case and min 3 Characters.)")
    String fullName;
    @NotEmpty(message="Address Cannot be Empty")
    String address;
    @NotEmpty(message="City Cannot be Empty")
    List<String> city;
    @NotEmpty(message="State Cannot be Empty")
    String state;
    @Pattern(regexp = "^[1-9]{1}[0-9]{5}$", message="Invalid Zip Code(First digit is non-zero, Should be 6 digit), example: 234098")
    String zip;
    @Pattern(regexp = "^[1-9]{2}[0-9]{10}$", message="Invalid Contact Number(Should have Country Code and must be 10 digit number) example: 919234567890")
    String contactNumber;
    @NotEmpty(message="Email Address Cannot be Empty")
    //List<String> emailAddress;
    String emailAddress;
}