package com.example.diplomnabe.Controllers;

import com.example.diplomnabe.Classes.User;
import com.example.diplomnabe.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/version1/user")
public class UserController
{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers()
    {
        return userService.getUsers();
    }

    @PostMapping
    public void registerNewUser(@RequestBody User user)
    {
        userService.addNewUser(user);
    }

    @DeleteMapping(path = "{UserId}")
    public void deleteUser(@PathVariable("UserId") Long UserId) throws Exception {
        userService.deleteUser(UserId);
    }
}
