package de.esports.aeq.admins.security;

import de.esports.aeq.admins.security.domain.RoleTa;
import de.esports.aeq.admins.security.jpa.RoleRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RunAsRoleAspect {

    private RoleRepository repository;

    @Autowired
    public RunAsRoleAspect(RoleRepository repository) {
        this.repository = repository;
    }

    @Around("@annotation(RunAsRole)")
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
        RoleTa role = repository.findOneByName(roleName)
                .orElseThrow(RuntimeException::new);

        SecurityContext context = new SecurityContextImpl();
        Authentication authentication = new UsernamePasswordAuthenticationToken(role.getName(),
                null, role.getPrivileges());
        context.setAuthentication(authentication);
        return context;
    }
}
