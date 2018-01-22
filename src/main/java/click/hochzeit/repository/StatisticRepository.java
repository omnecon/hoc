package click.hochzeit.repository;

import click.hochzeit.domain.Statistic;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Statistic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long>, JpaSpecificationExecutor<Statistic> {

}
