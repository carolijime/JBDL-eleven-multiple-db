package com.example.JBDLelevenmultipledb;

import com.example.JBDLelevenmultipledb.persondb.Person;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Random;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreatePersonRequest {

    @NotBlank(message = "FirstName should not be empty")
    private String firstName; //should not be empty

    private String lastName; //can be empty

//    private int age; //could be calculated so no need to be in the request

    @NotBlank(message = "Date of birth should not be empty")
    private String dob;//mandatory

    //example of how to have a field in the request, but not saving it in the DB, just using for doing some logic
//    @NotBlank
//    private String location;

    public Person to(){
        return Person.builder()
                .id(new Random().nextInt()) //WRONG! need to change this to upto-increment id TO-DO
                .firstName(firstName)
                .lastName(lastName)
                .dob(dob)
//                .countryCode(getCountryCodeFromLocation())
                .build();
    }

//    public String getCountryCodeFromLocation(){
//        if(this.location.equalsIgnoreCase("india")){
//            return  "+91";
//        }
//
//        return null;
//    }

}
