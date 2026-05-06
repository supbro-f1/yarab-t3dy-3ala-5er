package com.aastmt.renovation.service;

import org.springframework.stereotype.Service;
import com.aastmt.renovation.dto.UserRegisterDto;
import com.aastmt.renovation.model.User;
import com.aastmt.renovation.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userrepo;

    public UserService(UserRepository userrepo) {
        this.userrepo = userrepo;
    }

    // GetUserProfile by id
    public User getUserProfile(Long id) {
        return userrepo.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // get user by username
    public User getUserByUsername(String username) {
        return userrepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found " + username));
    }
    // update user prof
    public boolean updateUserProfile(Long id, UserRegisterDto dtoObj) {
        
        // user exists?
        User existing = userrepo.findById(id).orElse(null);
        if (existing == null) return false; 

        //  new username is already taken?
        if (userrepo.existsByUsername(dtoObj.getUsername())
            && !existing.getUsername().equals(dtoObj.getUsername())) {
            return false;
        }

        //  email is already used?
        if (userrepo.existsByEmail(dtoObj.getEmail())
            && !existing.getEmail().equals(dtoObj.getEmail())) {
            return false;
        }

        // phone no. is already used?
        if (userrepo.existsByPhoneNumber(dtoObj.getPhonenumber())
            && !existing.getPhoneNumber().equals(dtoObj.getPhonenumber())) {
            return false;
        }


        // update the user data
        existing.setFirstName(dtoObj.getFname());
        existing.setLastName(dtoObj.getLname());
        existing.setUsername(dtoObj.getUsername());
        existing.setPassword(dtoObj.getPassword());
        existing.setEmail(dtoObj.getEmail());
        existing.setPhoneNumber(dtoObj.getPhonenumber());

        userrepo.save(existing);
        return true;
    } 

    public boolean deleteUser(Long id) {
        if (!userrepo.existsById(id))
            return false;

        userrepo.deleteById(id);
        return true;
    }
    
}