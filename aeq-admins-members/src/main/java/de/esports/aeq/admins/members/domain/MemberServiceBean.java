package de.esports.aeq.admins.members.domain;

import de.esports.aeq.admins.common.EntityNotFoundException;
import de.esports.aeq.admins.members.jpa.MemberRepository;
import de.esports.aeq.admins.members.jpa.entity.MemberTa;
import de.esports.aeq.admins.members.jpa.entity.VerifiableAccountTa;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MemberServiceBean implements MemberService {

    private ModelMapper mapper;
    private final MemberRepository repository;

    @Autowired
    public MemberServiceBean(ModelMapper mapper, MemberRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @PostConstruct
    private void setupMapper() {
        var memberToEntityMap = mapper.createTypeMap(MemberTa.class, Member.class);
        memberToEntityMap.addMappings(mapper -> mapper.using(this::mapVerifiableAccount)
                .map(MemberTa::getTeamspeakAccount, Member::setTeamspeakAccount));

        var entityToMemberMap = mapper.createTypeMap(Member.class, MemberTa.class);
        entityToMemberMap.addMappings(mapper -> mapper.using(this::mapVerifiableAccountEntity)
                .map(Member::getTeamspeakAccount, MemberTa::setTeamspeakAccount));
    }

    @Override
    public Collection<Member> findAll() {
        return repository.findAll().stream().map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public Member findById(Long id) {
        MemberTa member = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        return map(member);
    }

    @Override
    public Member create(Member member) {
        member.setId(null);
        return null;
    }

    @Override
    public Member update(Member member) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    private Member map(MemberTa memberTa) {
        return mapper.map(memberTa, Member.class);
    }

    private VerifiableAccount mapVerifiableAccount(
            MappingContext<Object, Object> context) {
        VerifiableAccountTa source = (VerifiableAccountTa) context.getSource();
        return mapper.map(source, VerifiableAccountImpl.class);
    }

    private VerifiableAccountTa mapVerifiableAccountEntity(
            MappingContext<Object, Object> context) {
        VerifiableAccount source = (VerifiableAccount) context.getSource();
        return mapper.map(source, VerifiableAccountTa.class);
    }
}
