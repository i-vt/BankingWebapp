package com.example.platform.service;

import com.example.platform.model.AppUser;
import com.example.platform.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private AppUserRepository appUserRepository;

    // Register a user with the supplied UUID, setting other fields to NULL
    public AppUser registerUUID(UUID uuid) {
        AppUser user = new AppUser();
        user.setId(uuid);  // Use the supplied UUID
        user.setFullName(null);  // Set other details to null
        user.setAddress(null);
        user.setFaceImagePath(null);
        user.setIdCardImagePath(null);
        return appUserRepository.save(user);
    }

    // Populate the user's details using the supplied UUID
    public Optional<AppUser> populateUserDetails(UUID uuid, String fullName, String address, String faceImagePath, String idCardImagePath) {
        Optional<AppUser> userOptional = appUserRepository.findById(uuid);
        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();
            user.setFullName(fullName);
            user.setAddress(address);
            user.setFaceImagePath(faceImagePath);
            user.setIdCardImagePath(idCardImagePath);
            appUserRepository.save(user);
        }
        return userOptional;
    }

    public Optional<AppUser> findUserByUUID(UUID uuid) {
        return appUserRepository.findById(uuid);
    }
}
