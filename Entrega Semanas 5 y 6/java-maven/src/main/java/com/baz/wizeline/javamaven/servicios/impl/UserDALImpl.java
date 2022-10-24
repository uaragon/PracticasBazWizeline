package com.baz.wizeline.javamaven.servicios.impl;

import com.baz.wizeline.javamaven.model.UserDTO;
import com.baz.wizeline.javamaven.repository.UserDAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDALImpl implements UserDAL {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<UserDTO> getAllUsers() {
        return mongoTemplate.findAll(UserDTO.class);
    }

    @Override
    public List<UserDTO> getAccounts() {
        return null;
    }

    @Override
    public UserDTO getUserById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, UserDTO.class);
    }

    @Override
    public UserDTO addNewUser(UserDTO userDTO) {
        mongoTemplate.save(userDTO);
        // Now, userDTO object will contain the ID as well
        return userDTO;
    }

    @Override
    public Object getAllUserSettings(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        UserDTO userDTO = mongoTemplate.findOne(query, UserDTO.class);
        return userDTO != null ? userDTO.getUserSettings() : "UserDTO not found.";
    }

    @Override
    public String getUserSetting(String userId, String key) {
        Query query = new Query();
        query.fields().include("userSettings");
        query.addCriteria(Criteria.where("userId").is(userId).andOperator(Criteria.where("userSettings." + key).exists(true)));
        UserDTO userDTO = mongoTemplate.findOne(query, UserDTO.class);
        return userDTO != null ? userDTO.getUserSettings().get(key) : "Not found.";
    }

    @Override
    public String addUserSetting(String userId, String key, String value) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        UserDTO userDTO = mongoTemplate.findOne(query, UserDTO.class);
        if (userDTO != null) {
            userDTO.getUserSettings().put(key, value);
            mongoTemplate.save(userDTO);
            return "Key added.";
        } else {
            return "UserDTO not found.";
        }
    }
}