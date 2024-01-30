package com.example.diplomnabe.Services;

import com.example.diplomnabe.Authentication.AuthenticationRequest;
import com.example.diplomnabe.Authentication.AuthenticationResponse;
import com.example.diplomnabe.Authentication.RegisterRequest;
import com.example.diplomnabe.Classes.Role;
import com.example.diplomnabe.DTO.UserDTO;
import com.example.diplomnabe.Repositories.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.diplomnabe.Classes.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class AuthenticationService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        List<User> users = userRepository.findAll();
        try{
            for(int i = 0; i < users.size(); i++)
            {
                if(Objects.equals(users.get(i).getName(), request.getName()) || Objects.equals(users.get(i).getEmail(), request.getEmail()))
                {
                    throw new Exception("User with same name or email already exist");
                }
            }
        }
        catch (Exception e)
        {
            throw new Exception("User with same name or email already exist");
        }

        var user = new User(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getUserRecipesId(),user.getUserFavouritesRecipesId());
        return new AuthenticationResponse(jwtToken,userDTO);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getUserRecipesId(),user.getUserFavouritesRecipesId());
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken,userDTO);
    }
}
