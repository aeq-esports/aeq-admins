package de.esports.aeq.admins.users.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "aeq_user")
public class UserTa {

    @Id
    @GeneratedValue
    @Column(name = "aeq_user_id")
    private Long id;

    @Column
    private String ts3UniqueId;

    @Column
    private LocalDate age;
}
