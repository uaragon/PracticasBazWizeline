package com.wizeline.DTO;

import java.util.Map;

//Bean que al,acenará los parámetros recibidos en el request del servicio.
public class UserDTO {
    public String user;
    public String password;


    public UserDTO() {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDTO getParameters(Map<String, String> userParam) {
        UserDTO user = new UserDTO();
        user.setUser(userParam.get("user"));
        user.setPassword(userParam.get("password"));
        return user;
    }
}
