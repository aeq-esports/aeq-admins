package de.esports.aeq.admins.trials.workflow;

import de.esports.aeq.admins.common.CamundaRelated;
import de.esports.aeq.admins.security.RunAsRole;
import de.esports.aeq.admins.trials.service.TrialPeriodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("messaging")
public class MessageBean {

    private static final Logger LOG = LoggerFactory.getLogger(MessageBean.class);

    private TrialPeriodService service;

    @Autowired
    public MessageBean(TrialPeriodService service) {
        this.service = service;
    }

    @RunAsRole("ADMIN")
    @CamundaRelated
    public void sendStartedMessage(Long trialPeriodId) {
        var entity = service.findOne(trialPeriodId);
        LOG.info("Trial period started: {}", entity);
    }

    @RunAsRole("ADMIN")
    @CamundaRelated
    public void sendPendingMessage(Long trialPeriodId) {
        var entity = service.findOne(trialPeriodId);
        LOG.info("Trial period pending: {}", entity);
    }

    @RunAsRole("ADMIN")
    @CamundaRelated
    public void sendExtendedMessage(Long trialPeriodId) {
        var entity = service.findOne(trialPeriodId);
        LOG.info("Trial period extended: {}", entity);
    }

    @RunAsRole("ADMIN")
    @CamundaRelated
    public void sendApprovedMessage(Long trialPeriodId) {
        var entity = service.findOne(trialPeriodId);
        LOG.info("Trial period approved: {}", entity);
    }

    @RunAsRole("ADMIN")
    @CamundaRelated
    public void sendRejectedMessage(Long trialPeriodId) {
        var entity = service.findOne(trialPeriodId);
        LOG.info("Trial period rejected: {}", entity);
    }
}
