package com.store.springboot_ecommerce.service;


//import java.time.LocalDateTime;
//import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.springboot_ecommerce.dto.AuthRes;
import com.store.springboot_ecommerce.dto.LoginReq;
import com.store.springboot_ecommerce.dto.RegisterReq;
import com.store.springboot_ecommerce.model.User;
import com.store.springboot_ecommerce.repository.UserRepo;

@Service
public class UserService {
    private UserRepo userRepo ;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

     public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder , JwtService jwtService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService ;
    }
/* 
 public User register(String userName , String email , String password) {
        // check if email is exist
        if(userRepo.findByEmail(email).isPresent()){
            throw new RuntimeException("Email already exist");
        }

        //check if username is exist
        if(userRepo.findByUserName(userName).isPresent()){
            throw new RuntimeException("Username is found");
        }

        // bcrypt password
        String hashedPassword = passwordEncoder.encode(password);

        // create user entity and set fields

        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRole("user");
        user.setCreatedAt(LocalDateTime.now());

        // save user in db
        return userRepo.save(user) ;
    }

*/

    public void register(RegisterReq req){

        boolean exists = userRepo.findByEmail(req.getEmail()).isPresent();

       // boolean exists = userRepo.existsByEmail(email);

       // userRepo.existsByEmail(req.getEmail())

        if(exists){
            throw new RuntimeException("Email already exist");
        }    
        User user = new User();
        user.setEmail(req.getEmail());
        user.setUserName(req.getUserName());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole("USER");
        userRepo.save(user);

    }


    /*
    public boolean loginUser(String userName , String password){
        Optional<User> userOptional = userRepo.findByUserName(userName);
        if(userOptional.isEmpty()){
            return false;
        }

        User user = userOptional.get();
        return passwordEncoder.matches(password, user.getPassword());

    }
    */

    public AuthRes login(LoginReq req){
        User user = userRepo.findByEmail(req.getEmail())
        .orElseThrow(()-> new RuntimeException("invalid credential"));

        if(!passwordEncoder.matches(req.getPassword(), user.getPassword())){
            throw new RuntimeException("invalid password");
        }

        String token = jwtService.generateToken(user);

        return new AuthRes(token, user.getUserName(), user.getRole());

    }


   

    
    
}