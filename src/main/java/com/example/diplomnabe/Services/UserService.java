package com.example.diplomnabe.Services;

import com.example.diplomnabe.Classes.Recipe;
import com.example.diplomnabe.DTO.UserDTO;
import com.example.diplomnabe.Repositories.UserRepository;
import com.example.diplomnabe.Classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService
{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    public List<User> getUsers()
    {
        return userRepository.findAll();
    }

    public List<UserDTO> getUsersDTO()
    {
        List<UserDTO> list_of_DTO = new ArrayList<>();
        List<User> users = getUsers();
        for (User user : users) {
            list_of_DTO.add(user.convertUserToUserDTO());
        }
        return list_of_DTO;
    }

    public void addNewUser(User user)
    {
        userRepository.save(user);
    }


    public void deleteUser(Long UserId) throws Exception {
        if(userRepository.existsById(UserId))
            userRepository.deleteById(UserId);
        else
            throw new Exception("user with id " + UserId + " does not exists");
    }

    public List<Recipe> getRecipesOfUser(Long userId)
    {
        return userRepository.getReferenceById(userId).getUser_recipes();
    }

    public List<Recipe> getFavouritesRecipesOfUser(Long userId)
    {
        return userRepository.getReferenceById(userId).getFavourites();
    }
}
