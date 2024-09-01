package modak.challenge.notification_service.Generic.Infrastructure.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IPostgreSQLGenericRepository<T, ID> extends JpaRepository<T, ID> { }
