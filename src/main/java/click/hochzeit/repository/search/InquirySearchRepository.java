package click.hochzeit.repository.search;

import click.hochzeit.domain.Inquiry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Inquiry entity.
 */
public interface InquirySearchRepository extends ElasticsearchRepository<Inquiry, Long> {
}
