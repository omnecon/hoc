package click.hochzeit.repository.search;

import click.hochzeit.domain.Feature;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Feature entity.
 */
public interface FeatureSearchRepository extends ElasticsearchRepository<Feature, Long> {
}
