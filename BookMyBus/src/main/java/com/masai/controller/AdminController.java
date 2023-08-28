package com.masai.controller;

import com.masai.exception.ResourceNotFoundException;
import com.masai.model.Admin;
import com.masai.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registerAdmin")
    public ResponseEntity<Admin> createAdmin(@Valid @RequestBody Admin admin) throws ResourceNotFoundException {
        admin.setDeleted(false);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return new ResponseEntity<Admin>(adminService.addAdmin(admin), HttpStatus.CREATED);
    }

    @GetMapping("/signInAdmin")
    public ResponseEntity<String> logInAsAdmin(Authentication auth) throws ResourceNotFoundException {
        Admin admin = adminService.findByEmail(auth.getName()).get();
        return new ResponseEntity<>(admin.getEmail() + " Logged In Successfully", HttpStatus.ACCEPTED);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Integer adminId)throws ResourceNotFoundException{
        return new ResponseEntity<Admin>(adminService.findByAdminId(adminId),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{adminId}")
    public ResponseEntity<Admin> deleteAdmin(@PathVariable Integer adminId)throws ResourceNotFoundException{
        return new ResponseEntity<Admin>(adminService.deleteAdmin(adminId),HttpStatus.OK);
    }



    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins()throws ResourceNotFoundException{
        return new ResponseEntity<List<Admin>>(adminService.viewAllAdmin(),HttpStatus.OK);
    }



}
