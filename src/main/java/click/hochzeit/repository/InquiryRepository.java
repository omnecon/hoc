package click.hochzeit.repository;

import click.hochzeit.domain.Inquiry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Inquiry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long>, JpaSpecificationExecutor<Inquiry> {

}
