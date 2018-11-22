package de.esports.aeq.admins.trials.workflow;

import de.esports.aeq.admins.common.CamundaRelated;
import de.esports.aeq.admins.trials.service.TrialPeriodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("services")
public class ServiceBean {

    private static final Logger LOG = LoggerFactory.getLogger(MessageBean.class);

    private TrialPeriodService service;

    @Autowired
    public ServiceBean(TrialPeriodService service) {
        this.service = service;
    }

    @CamundaRelated
    public void extendTrialPeriod(Long trialPeriodId) {
        var entity = service.findOne(trialPeriodId);
        LOG.info("Trial period extended: {}", entity);
    }
}
