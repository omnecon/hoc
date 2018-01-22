package click.hochzeit.repository.search;

import click.hochzeit.domain.DataImport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DataImport entity.
 */
public interface DataImportSearchRepository extends ElasticsearchRepository<DataImport, Long> {
}
