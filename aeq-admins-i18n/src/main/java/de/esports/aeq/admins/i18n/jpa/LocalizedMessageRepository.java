package de.esports.aeq.admins.i18n.jpa;

import de.esports.aeq.admins.i18n.jpa.entity.LocalizedMessageTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizedMessageRepository extends JpaRepository<LocalizedMessageTa, String> {

}
