package com.example.service;

import com.example.dto.auth.AuthRegistrationDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileStatus;
import com.example.exceptions.ItemAlreadyExistException;
import com.example.exceptions.ProfileCreateException;
import com.example.repository.ProfileRepository;
import com.example.util.JwtTokenUtil;
import com.example.util.MD5Util;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private EmailService emailService;

    public String registration(AuthRegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhone(dto.getPhone());

        if (optional.isPresent()) {
            ProfileEntity profile = optional.get();
            if (profile.getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
                profileRepository.delete(profile);
            } else {
                throw new ItemAlreadyExistException("Profile Already registered");
            }
        }

        ProfileEntity profile = new ProfileEntity();
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setPhone(dto.getPhone());
        profile.setEmail(dto.getEmail());
        profile.setPassword(MD5Util.encode(dto.getPassword()));
        profile.setPhotoId(dto.getPhotoId());

        profileRepository.save(profile);

        StringBuilder sb = new StringBuilder();
        sb.append("<h1 style=\"text-align: center\">Complete Registration</h1>");
        String link = String.format("<a href=\"http://localhost:8080/auth/verification/email/%s\"> Click there </a>", JwtTokenUtil.encode(profile.getPhone(), profile.getRole()));
        sb.append(link);
        emailService.sendEmailMime(dto.getEmail(), "Complete registration", sb.toString());


        if (profile.getId() == 0) {
            throw new ProfileCreateException("Something went wrong");
        }
        return "Successfully registered";
    }

    public String verification(String jwt) {
        Integer id;
        try {
            id = JwtTokenUtil.decodeForEmailVerification(jwt);
        } catch (JwtException e) {
            return "Verification failed";
        }

//        ProfileEntity exists = profileService.get(id);
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        ProfileEntity exists = optional.get();
        if (!exists.getStatus().equals(ProfileStatus.NOT_ACTIVE)) {
            return "Verification failed";
        }

        exists.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(exists);

        return "Verification success";
    }
}
