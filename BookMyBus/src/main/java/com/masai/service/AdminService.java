package com.masai.service;

import com.masai.exception.ResourceNotFoundException;
import com.masai.model.Admin;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    Admin addAdmin(Admin admin)throws ResourceNotFoundException;

    Admin deleteAdmin(Integer adminId)throws ResourceNotFoundException;

    Optional<Admin> findByEmail(String Email)throws ResourceNotFoundException;

    Admin findByAdminId(Integer adminId)throws ResourceNotFoundException;

    List<Admin> viewAllAdmin()throws ResourceNotFoundException;
}
