package de.esports.aeq.admins.member.impl.jpa;

import de.esports.aeq.admins.member.impl.jpa.entity.MemberProfileTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberProfileRepository extends JpaRepository<MemberProfileTa, Long> {

}
