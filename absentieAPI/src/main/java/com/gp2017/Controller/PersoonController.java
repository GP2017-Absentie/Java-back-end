package com.gp2017.Controller;

import com.gp2017.RequestEntity.LoginRequest;
import com.gp2017.Entity.Persoon;
import com.gp2017.Service.PersoonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/persoon")
public class PersoonController {

    @Autowired
    private PersoonService persoonService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public Collection getAll(){
        HashMap<Integer, Persoon> persons = new HashMap<Integer, Persoon>();
        ArrayList<Persoon> allPersons = persoonService.getAll();
        for (Persoon person : allPersons){
            persons.put(person.getId(), person);
        }
        return persons.values();
    }
    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Collection getById(@PathVariable("id") int id){
        HashMap<Integer, Persoon> persoon = new HashMap<Integer, Persoon>() {
            {
                put(1, persoonService.getById(id));
            }
        };
        return persoon.values();
    }
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Collection getByEmail(@PathVariable("email")String email){
        HashMap<Integer, Persoon> persoon = new HashMap<Integer, Persoon>() {
            {
                put(1, persoonService.getByEmail(email));
            }
        };
        return persoon.values();
    }
    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persoon> login(@RequestBody LoginRequest request) {
        Persoon p = null;
        if (request != null) {
            p = persoonService.login(request.getEmail(), request.getPassword());
            if (p != null){
                return new ResponseEntity<Persoon>(p, HttpStatus.OK);
            }
        }

        throw new IllegalArgumentException("Password or email incorrect");
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

}
