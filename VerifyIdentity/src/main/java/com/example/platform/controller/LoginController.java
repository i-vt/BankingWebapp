package com.example.platform.controller;

import com.example.platform.model.AppUser;
import com.example.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    // Register a user with UUID (other fields are null)
    @PostMapping("/register-uuid")
    public String registerUUID(@RequestParam UUID uuid) {
        AppUser user = userService.registerUUID(uuid);
        return "UUID registered successfully. Your UUID is: " + user.getId();
    }

    // Populate user's full name, address, face image, and ID card image
    @PostMapping("/populate-details/{uuid}")
    public String populateUserDetails(@PathVariable UUID uuid,
                                      @RequestParam String fullName,
                                      @RequestParam String address,
                                      @RequestParam("faceImage") MultipartFile faceImage,
                                      @RequestParam("idCardImage") MultipartFile idCardImage) throws IOException {
        // Save uploaded files
        String faceImagePath = saveFile(faceImage, "face_" + uuid);
        String idCardImagePath = saveFile(idCardImage, "idcard_" + uuid);

        Optional<AppUser> userOpt = userService.populateUserDetails(uuid, fullName, address, faceImagePath, idCardImagePath);
        if (userOpt.isEmpty()) {
            return "User with UUID " + uuid + " not found.";
        }
        return "User details populated successfully for UUID: " + uuid;
    }

    private String saveFile(MultipartFile file, String fileName) throws IOException {
        File dest = new File("uploads/" + fileName + "_" + file.getOriginalFilename());
        file.transferTo(dest);
        return dest.getAbsolutePath();
    }
}
