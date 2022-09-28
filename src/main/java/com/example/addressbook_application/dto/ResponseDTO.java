package com.example.addressbook_application.dto;

import com.example.addressbook_application.model.AddressBook;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class ResponseDTO {
    String message;
    Object response;

    public ResponseDTO(String message, String response) {
        this.message = message;
        this.response = response;
    }

    public ResponseDTO(String message, Optional<AddressBook> response) {
        this.message = message;
        this.response = response;
    }
    public ResponseDTO(String message, List<AddressBook> response) {
        this.message = message;
        this.response = response;
    }
}
