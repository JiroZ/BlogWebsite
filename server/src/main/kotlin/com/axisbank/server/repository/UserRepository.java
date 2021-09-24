package com.axisbank.server.repository;

import com.axisbank.server.DTO.user.BlogUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<BlogUser, String> {
}
