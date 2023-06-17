package com.proyecto.prueba.controller;

import com.proyecto.prueba.Exceptions.ExceptionsClass;
import com.proyecto.prueba.model.dto.AuthResponseDTO;
import com.proyecto.prueba.model.dto.ExceptionResponseDTO;
import com.proyecto.prueba.model.dto.SignUpDTO;
import com.proyecto.prueba.model.dto.LoginDTO;
import com.proyecto.prueba.model.entity.Rol;
import com.proyecto.prueba.model.entity.User;
import com.proyecto.prueba.model.entity.UserRol;
import com.proyecto.prueba.repository.UserRepository;
import com.proyecto.prueba.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * The controller class for user authentication and registration.
 */
@RestController
@RequestMapping("/api/auth/")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtGenerator jwtGenerator;


    /**
     * Endpoint to register an admin user.
     *
     * @param signUpDTO The SignUpDTO object containing the admin user details.
     * @return ResponseEntity with a success message on success, or an error message on failure.
     */
    @PostMapping("registerAdm")
    public ResponseEntity<String> registrarAdmin(@RequestBody SignUpDTO signUpDTO) {
        User user = new User();
        user.setUsername(signUpDTO.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        Set<UserRol> userRols = new HashSet<>();
        UserRol userrol1 = new UserRol();
        Rol rol1 = new Rol();
        rol1.setIdRol(1L);
        rol1.setName("ADMIN");
        userrol1.setUser(user);
        userrol1.setRol(rol1);
        userRols.add(userrol1);
        user.getUserRols().addAll(userRols);
        userRepository.save(user);
        return new ResponseEntity<>("Registro de admin exitoso", HttpStatus.OK);
    }

    /**
     * Endpoint to authenticate a user and generate a JWT token.
     *
     * @param loginDTO The LoginDTO object containing the user's login credentials.
     * @return ResponseEntity with the JWT token on success, or an error message on failure.
     */
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try{
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generarToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
        }catch (Exception e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionResponseDTO.builder()
                .status(HttpStatus.UNAUTHORIZED.value()).error("Credenciales invalidas").build());
        }
    }

    }
