package com.usersys.backend.controller.user;

import com.usersys.backend.service.user.RegisterService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
  @Autowired
  private RegisterService registerService;

  @PostMapping("/user/register/")
  public Map<String, String> register(@RequestParam Map<String, String> map) {
    String username = map.get("username");
    String password = map.get("password");
    String confirmPassword = map.get("confirmedPassword");
    return registerService.register(username, password, confirmPassword);
  }

}
