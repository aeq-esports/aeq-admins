package de.esports.aeq.admins.common;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface CrudService<T, ID> {

    Collection<T> getAll();

    List<T> getAll(Sort sort);

    Page<T> getAll(Pageable pageable);

    List<T> getAll(Example<T> example);

    List<T> getAll(Example<T> example, Sort sort);

    Collection<T> getAllByIds(Collection<ID> ids);

    Optional<T> getOneById(ID id);

    Optional<T> getOne(T object);

    T create(T object);

    T update(T object);

    void remove(ID id);

    default T createOrUpdate(T object) {
        Optional<T> one = getOne(object);
        if (one.isPresent()) {
            return update(object);
        }
        return create(object);
    }

    default T createIfAbsent(ID id, T object) {
        requireNonNull(id, "The id must not be null");
        requireNonNull(object, "The object must not be null");
        return getOneById(id).orElseGet(() -> create(object));
    }
}
