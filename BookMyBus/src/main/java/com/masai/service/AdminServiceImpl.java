package com.masai.service;

import com.masai.exception.ResourceNotFoundException;
import com.masai.model.Admin;
import com.masai.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminRepository repository;

    @Override
    public Admin addAdmin(Admin admin) throws ResourceNotFoundException {
        Optional<Admin> optional =repository.findByEmail(admin.getEmail());
        if(optional.isPresent()){
            throw new ResourceNotFoundException("Admin already registered");
        }
        return repository.save(admin);
    }

    @Override
    public Admin deleteAdmin(Integer adminId) throws ResourceNotFoundException {
        Optional<Admin> optional =repository.findById(adminId);
        if(optional.isEmpty()){
            throw new ResourceNotFoundException("No admin found "+adminId);
        }
        Admin admin=optional.get();
        admin.setDeleted(true);
        return admin;
    }

    @Override
    public Optional<Admin> findByEmail(String email) throws ResourceNotFoundException {
        Optional<Admin> optional =repository.findByEmail(email);
        if(optional.isEmpty()){
            throw new ResourceNotFoundException("No admin found with email "+email);
        }
        return optional;
    }

    @Override
    public Admin findByAdminId(Integer adminId) throws ResourceNotFoundException {
        return repository.findById(adminId).orElseThrow(()-> new ResourceNotFoundException("No admin found with adminId " +adminId));
    }

    @Override
    public List<Admin> viewAllAdmin() throws ResourceNotFoundException{
        List<Admin> list = repository.findAll();
        if(list.isEmpty())throw new ResourceNotFoundException("No Admin Found");
        return list;
    }
}
