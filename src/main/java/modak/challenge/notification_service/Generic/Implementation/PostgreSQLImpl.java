package modak.challenge.notification_service.Generic.Implementation;

import modak.challenge.notification_service.Generic.Infrastructure.Repository.IPostgreSQLGenericRepository;
import modak.challenge.notification_service.Generic.Service.ICRUDService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class PostgreSQLImpl<T, ID> implements ICRUDService<T, ID> {
    protected abstract IPostgreSQLGenericRepository<T, ID> getRepository();
    @Override
    public T create(T t) {
        return getRepository().save(t);
    }

    @Override
    public List<T> read()  {
        return getRepository().findAll();
    }

    @Override
    public Page<T> read(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override
    public T readById(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    @Override
    public T update(T t) throws Exception {
        return getRepository().save(t);
    }

    @Override
    public void delete(ID id) throws Exception {
        getRepository().deleteById(id);
    }
}
