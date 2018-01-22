package click.hochzeit.repository.search;

import click.hochzeit.domain.Statistic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Statistic entity.
 */
public interface StatisticSearchRepository extends ElasticsearchRepository<Statistic, Long> {
}
