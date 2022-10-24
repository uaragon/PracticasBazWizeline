package com.baz.wizeline.javamaven.repository;
import com.baz.wizeline.javamaven.model.UserDTO;

import java.util.List;

public interface UserDAL {
    List<UserDTO> getAllUsers();
    List<UserDTO> getAccounts();
    UserDTO getUserById(String userId);
    UserDTO addNewUser(UserDTO userDTO);
    Object getAllUserSettings(String userId);
    String getUserSetting(String userId, String key);
    String addUserSetting(String userId, String key, String value);

}
