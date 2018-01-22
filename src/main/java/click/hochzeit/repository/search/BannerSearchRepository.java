package click.hochzeit.repository.search;

import click.hochzeit.domain.Banner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Banner entity.
 */
public interface BannerSearchRepository extends ElasticsearchRepository<Banner, Long> {
}
