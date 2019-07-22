package de.esports.aeq.admins.member.impl;

import de.esports.aeq.admins.member.api.UserProfile;
import de.esports.aeq.admins.member.api.service.UserProfileService;
import de.esports.aeq.admins.security.api.event.UserCreatedEvent;
import de.esports.aeq.admins.security.api.event.UserDeletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class UserEventHandlerBean {

    private static final Logger LOG = LoggerFactory.getLogger(UserEventHandlerBean.class);

    private final UserProfileService profileService;

    //-----------------------------------------------------------------------

    @Autowired
    public UserEventHandlerBean(UserProfileService profileService) {
        this.profileService = profileService;
    }

    //-----------------------------------------------------------------------

    @KafkaListener(topics = UserCreatedEvent.KEY)
    private void createMissingUserProfile(@Payload UserCreatedEvent event) {
        Long userId = event.getUser().getId();
        UserProfile profile = profileService.getOneById(userId)
            .orElseGet(() -> profileService.create(UserProfile.empty(userId)));
        LOG.debug("Created empty user profile for user with user id {}: {}", userId, profile);
    }

    @KafkaListener(topics = UserDeletedEvent.KEY)
    private void deleteExistingUserProfile(@Payload UserDeletedEvent event) {
        Long userId = event.getUser().getId();
        profileService.remove(userId);
    }

}
