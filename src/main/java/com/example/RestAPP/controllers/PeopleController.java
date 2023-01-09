package com.example.RestAPP.controllers;

import com.example.RestAPP.models.Person;
import com.example.RestAPP.services.PeopleService;
import com.example.RestAPP.util.PersonErrorResponse;
import com.example.RestAPP.util.PersonNotCreatedExeption;
import com.example.RestAPP.util.PersonNotFoundExeption;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public List<Person> getPeople() {
        return peopleService.findAll();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        return peopleService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Person person,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorsStr = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorsStr.append(error.getField())
                        .append("-")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new PersonNotCreatedExeption(errorsStr.toString());
        }
        peopleService.save(person);

        return ResponseEntity.ok(HttpStatus.OK); // 200 - status
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleExeption(PersonNotFoundExeption e) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id wasn' t found!",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);  // - 404 status
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleExeption(PersonNotCreatedExeption e) {
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  
    }


}
