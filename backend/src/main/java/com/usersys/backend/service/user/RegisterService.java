package com.usersys.backend.service.user;

import java.util.Map;

public interface RegisterService {
  Map<String, String> register(String username, String password, String confirmedPassword, String role);

}
