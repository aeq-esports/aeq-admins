package de.esports.aeq.admins.security.impl.service;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import de.esports.aeq.admins.common.DuplicateKeyException;
import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.security.api.UserRole;
import de.esports.aeq.admins.security.api.event.UserRoleDeletedEvent;
import de.esports.aeq.admins.security.api.service.UserRoleService;
import de.esports.aeq.admins.security.impl.jpa.PrivilegeRepository;
import de.esports.aeq.admins.security.impl.jpa.UserRoleRepository;
import de.esports.aeq.admins.security.impl.jpa.entity.PrivilegeTa;
import de.esports.aeq.admins.security.impl.jpa.entity.UserRoleTa;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceBean implements UserRoleService {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityServiceBean.class);

    private static final String ROLE_NOT_NULL = "The role must not be null";
    private static final String ROLE_NAME_NOT_NULL = "The role name must not be null";

    private final ModelMapper mapper;
    private final UserRoleRepository userRoleRepository;
    private final PrivilegeRepository privilegeRepository;

    @Autowired
    private KafkaTemplate<String, Object> template;

    public UserRoleServiceBean(ModelMapper mapper,
        UserRoleRepository userRoleRepository,
        PrivilegeRepository privilegeRepository) {
        this.mapper = mapper;
        this.userRoleRepository = userRoleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Collection<UserRole> getAll() {
        return userRoleRepository.findAll().stream().map(UserRoleTa::getName)
            .map(UserRole::new).collect(toList());
    }

    @Override
    public List<UserRole> getAll(Sort sort) {
        return null;
    }

    @Override
    public Page<UserRole> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends UserRole> List<S> getAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends UserRole> List<S> getAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Collection<UserRole> getAllByIds(Collection<String> userRoleNames) {
        return userRoleRepository.findAllByNames(userRoleNames).stream()
            .map(this::toUserRole).collect(toList());
    }

    @Override
    public Optional<UserRole> getOneById(String name) {
        return userRoleRepository.findOneByName(name).map(UserRoleTa::getName).map(UserRole::new);
    }

    @Override
    public Optional<UserRole> getOne(UserRole userRole) {
        return getOneById(userRole.getAuthority());
    }

    @Override
    public UserRole create(UserRole userRole) {
        requireNonNull(userRole, ROLE_NOT_NULL);

        getOneById(userRole.getAuthority()).ifPresent(e -> {
            throw new DuplicateKeyException(e.getAuthority());
        });
    }

    @Override
    public UserRole update(UserRole userRole) {
        requireNonNull(userRole, ROLE_NOT_NULL);

        String authority = userRole.getAuthority();
        requireNonNull(authority, ROLE_NAME_NOT_NULL);

        UserRoleTa existing = userRoleRepository.findOneByName(authority)
            .orElseThrow(() -> new EntityNotFoundException(authority));

        UserRoleTa update = toUserRoleTa(userRole);
        mapper.map(update, existing);

        return toUserRole(existing);
    }

    @Override
    public void remove(String roleName) {
        requireNonNull(roleName, ROLE_NAME_NOT_NULL);

        UserRoleTa entity = userRoleRepository.findOneByName(roleName)
            .orElseThrow(() -> new EntityNotFoundException(roleName));

        UserRole role = new UserRole(entity.getName());
        userRoleRepository.deleteById(entity.getId());

        UserRoleDeletedEvent event = new UserRoleDeletedEvent(role);
        template.send(UserRoleDeletedEvent.KEY, event);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> result = new HashSet<>(getAll());

        privilegeRepository.findAll().stream().map(PrivilegeTa::getName)
            .map(SimpleGrantedAuthority::new).forEach(result::add);

        return result;
    }

    //-----------------------------------------------------------------------

    private UserRole toUserRole(UserRoleTa userRoleTa) {

    }

    private UserRoleTa toUserRoleTa(UserRole userRole) {

    }
}
