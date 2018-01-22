package click.hochzeit.service;

import click.hochzeit.domain.DataImport;
import click.hochzeit.repository.DataImportRepository;
import click.hochzeit.repository.search.DataImportSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DataImport.
 */
@Service
@Transactional
public class DataImportService {

    private final Logger log = LoggerFactory.getLogger(DataImportService.class);

    private final DataImportRepository dataImportRepository;

    private final DataImportSearchRepository dataImportSearchRepository;

    public DataImportService(DataImportRepository dataImportRepository, DataImportSearchRepository dataImportSearchRepository) {
        this.dataImportRepository = dataImportRepository;
        this.dataImportSearchRepository = dataImportSearchRepository;
    }

    /**
     * Save a dataImport.
     *
     * @param dataImport the entity to save
     * @return the persisted entity
     */
    public DataImport save(DataImport dataImport) {
        log.debug("Request to save DataImport : {}", dataImport);
        DataImport result = dataImportRepository.save(dataImport);
        dataImportSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the dataImports.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DataImport> findAll(Pageable pageable) {
        log.debug("Request to get all DataImports");
        return dataImportRepository.findAll(pageable);
    }

    /**
     * Get one dataImport by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DataImport findOne(Long id) {
        log.debug("Request to get DataImport : {}", id);
        return dataImportRepository.findOne(id);
    }

    /**
     * Delete the dataImport by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DataImport : {}", id);
        dataImportRepository.delete(id);
        dataImportSearchRepository.delete(id);
    }

    /**
     * Search for the dataImport corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DataImport> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DataImports for query {}", query);
        Page<DataImport> result = dataImportSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
