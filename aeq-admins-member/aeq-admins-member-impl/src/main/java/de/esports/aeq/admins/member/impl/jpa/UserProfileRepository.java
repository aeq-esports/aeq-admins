package de.esports.aeq.admins.member.impl.jpa;

import de.esports.aeq.admins.member.impl.jpa.entity.UserProfileTa;
import java.util.Optional;
import java.util.stream.DoubleStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileTa, Long> {

}
