package de.esports.aeq.admins.security.impl.support;

import de.esports.aeq.admins.security.api.annotation.RunAsRole;
import de.esports.aeq.admins.security.impl.jpa.UserRoleRepository;
import de.esports.aeq.admins.security.impl.jpa.entity.PrivilegeTa;
import de.esports.aeq.admins.security.impl.jpa.entity.UserRoleTa;
import java.lang.reflect.Method;
import java.util.stream.Collectors;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RunAsRoleAspect {

    private UserRoleRepository repository;

    @Autowired
    public RunAsRoleAspect(UserRoleRepository repository) {
        this.repository = repository;
    }

    @Around("@annotation(de.esports.aeq.admins.security.api.annotation.RunAsRole)")
    public Object setupRunAsRole(ProceedingJoinPoint joinPoint) throws Throwable {
        SecurityContext context = SecurityContextHolder.getContext();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RunAsRole annotation = method.getAnnotation(RunAsRole.class);

        SecurityContext replacement = createTemporarySecurityContext(annotation);
        try {
            SecurityContextHolder.setContext(replacement);
            return joinPoint.proceed();
        } finally {
            SecurityContextHolder.setContext(context);
        }
    }

    private SecurityContext createTemporarySecurityContext(RunAsRole annotation) {
        String roleName = annotation.value();
        UserRoleTa role = repository.findOneByName(roleName)
            .orElseThrow(RuntimeException::new);

        SecurityContext context = new SecurityContextImpl();

        var privileges = role.getPrivileges().stream().map(PrivilegeTa::getName)
            .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

        Authentication authentication = new UsernamePasswordAuthenticationToken(role.getName(),
            null, privileges);
        context.setAuthentication(authentication);
        return context;
    }
}
