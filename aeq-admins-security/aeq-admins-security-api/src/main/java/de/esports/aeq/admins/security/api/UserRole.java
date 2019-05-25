package de.esports.aeq.admins.security.api;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

public class UserRole implements GrantedAuthority {

    private static final Logger LOG = LoggerFactory.getLogger(UserRole.class);

    public static final String PREFIX = "ROLE_";

    private static final String NAME_NOT_NULL = "The name must not be null";
    private static final String NAME_NOT_BLANK = "The name must not be blank";
    private static final String AUTHORITY_NOT_NULL = "The authority must not be null";
    private static final String AUTHORITIES_NOT_NULL = "The authorities must not be null";
    private static final String FLAG_NOT_NULL = "The flag must not be null";
    private static final String FLAGS_NOT_NULL = "The flags must not be null";

    public static boolean isUserRole(GrantedAuthority authority) {
        return authority.getAuthority().startsWith(UserRole.PREFIX);
    }

    private static String addPrefix(String name) {
        requireNonNull(name, NAME_NOT_NULL);
        if (name.isBlank()) {
            throw new IllegalArgumentException(NAME_NOT_BLANK);
        }
        return name.toUpperCase().startsWith(PREFIX) ? name : PREFIX + name;
    }

    private final String name;
    private Multimap<String, GrantedAuthority> authorities = HashMultimap.create();

    private Set<UserRoleFlag> flags = EnumSet.noneOf(UserRoleFlag.class);

    public UserRole(String name) {
        this.name = addPrefix(name);
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return unmodifiableCollection(authorities.get(name));
    }

    public Collection<GrantedAuthority> getAuthorities(String roleName) {
        return unmodifiableCollection(authorities.get(roleName));
    }

    public Map<String, Collection<GrantedAuthority>> getAuthorityMap() {
        return unmodifiableMap(authorities.asMap());
    }

    public void addAuthority(GrantedAuthority authority) {
        if (authority.getAuthority().startsWith(PREFIX)) {
            LOG.warn("Adding a nested user role is not recommended: {}, for role {}",
                authority.getAuthority(), name);
        }
        this.authorities.put(name, authority);
    }

    public void addAuthority(String roleName, GrantedAuthority authority) {
        String prefixedRoleName = addPrefix(roleName);
        requireNonNull(authority, AUTHORITY_NOT_NULL);
        authorities.put(prefixedRoleName, authority);
    }

    public void addAuthority(String roleName, Collection<? extends GrantedAuthority> authorities) {
        String prefixedRoleName = addPrefix(roleName);
        requireNonNull(authorities, AUTHORITIES_NOT_NULL);
        this.authorities.putAll(prefixedRoleName, authorities);
    }

    public void addFlag(UserRoleFlag flag) {
        requireNonNull(flag, FLAG_NOT_NULL);
        this.flags.add(flag);
    }

    public void addFlags(Collection<? extends UserRoleFlag> flags) {
        requireNonNull(flags, FLAGS_NOT_NULL);
        this.flags.addAll(flags);
    }

    public Set<UserRoleFlag> getFlags() {
        return unmodifiableSet(flags);
    }

    public boolean containsFlag(UserRoleFlag flag) {
        requireNonNull(flag, FLAG_NOT_NULL);
        return this.flags.contains(flag);
    }

    public void removeFlag(UserRoleFlag flag) {
        requireNonNull(flag, FLAG_NOT_NULL);
        this.flags.remove(flag);
    }
}
