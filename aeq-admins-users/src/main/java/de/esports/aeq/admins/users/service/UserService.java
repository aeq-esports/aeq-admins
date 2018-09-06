package de.esports.aeq.admins.users.service;

import com.querydsl.core.types.Predicate;
import de.esports.aeq.admins.users.domain.UserTa;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserTa> findAll();

    List<UserTa> findAll(Predicate predicate);

    Optional<UserTa> findOne(Predicate predicate);

    UserTa findOneById(Long id);

    UserTa findOneByTS3UId(String ts3UId);

    UserTa create(UserTa user);

    UserTa update(UserTa user);

    void delete(Long userId);
}
