package de.esports.aeq.admins.common;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface CrudService<T, ID> {

    T create(T object);

    Collection<T> getAll();

    List<T> getAll(Sort sort);

    Page<T> getAll(Pageable pageable);

    <S extends T> List<S> getAll(Example<S> example);

    <S extends T> List<S> getAll(Example<S> example, Sort sort);

    Collection<T> getAllByIds(Collection<ID> ids);

    Optional<T> getOneById(ID id);

    Optional<T> getOne(T object);

    T update(T object);

    void remove(ID id);

    default T createOrUpdate(T object) {
        Optional<T> one = getOne(object);
        if (one.isPresent()) {
            return update(object);
        }
        return create(object);
    }
}
