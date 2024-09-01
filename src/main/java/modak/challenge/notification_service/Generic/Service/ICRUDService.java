package modak.challenge.notification_service.Generic.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICRUDService<T, ID> {
    T create(T t) throws Exception;

    List<T> read() throws Exception;

    Page<T> read(Pageable pageable) throws Exception;

    T readById(ID id) throws Exception;

    T update (T t) throws Exception;

    void delete(ID id) throws Exception;
}