package com.choimory.itemvaluechecker.api.userapi.user.repository;

import com.choimory.itemvaluechecker.api.userapi.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
