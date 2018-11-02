package de.esports.aeq.admins.forum.domain;

import de.esports.aeq.admins.security.domain.UserTa;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "aeq_comment")
public class CommentTa implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "aeq_comment_id")
    private Long id;

    private UserTa user;

    private String text;
}
