package com.baz.wizeline.javamaven.repository;

import com.baz.wizeline.javamaven.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {
}