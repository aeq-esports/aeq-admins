package de.esports.aeq.admins.i18n.impl.jpa;

import de.esports.aeq.admins.i18n.api.jpa.LocalizedMessageTa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizedMessageRepository extends JpaRepository<LocalizedMessageTa, String> {

}
