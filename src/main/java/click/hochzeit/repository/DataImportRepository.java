package click.hochzeit.repository;

import click.hochzeit.domain.DataImport;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DataImport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataImportRepository extends JpaRepository<DataImport, Long>, JpaSpecificationExecutor<DataImport> {

}
