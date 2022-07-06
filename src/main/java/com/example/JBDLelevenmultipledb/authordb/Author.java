package com.example.JBDLelevenmultipledb.authordb;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
@Builder
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Homework: below, why this change (colum unique) does not trigger the update of the table in the DB? (using UPDATE as ddl-auto)
    //Update will not remove anything.
    // it will also not change the unique column because of inconsistency (because you might already have non-unique values in the column you would like to change
    //flyway, liquibase are tools that could help doing that so we do not need to go to the terminal and make the changes manually
    @Column(unique = true)
    private String name;

    private int age;

    private String country;
}