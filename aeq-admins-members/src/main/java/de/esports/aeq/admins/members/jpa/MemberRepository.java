package de.esports.aeq.admins.members.jpa;

import de.esports.aeq.admins.members.jpa.entity.MemberTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberTa, Long> {

    MemberTa findByFirstName(String firstName);
}
