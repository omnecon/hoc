package click.hochzeit.repository.search;

import click.hochzeit.domain.Gallery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Gallery entity.
 */
public interface GallerySearchRepository extends ElasticsearchRepository<Gallery, Long> {
}
