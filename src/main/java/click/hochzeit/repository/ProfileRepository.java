package click.hochzeit.repository;

import click.hochzeit.domain.Profile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Profile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>, JpaSpecificationExecutor<Profile> {
    @Query("select distinct profile from Profile profile left join fetch profile.features")
    List<Profile> findAllWithEagerRelationships();

    @Query("select profile from Profile profile left join fetch profile.features where profile.id =:id")
    Profile findOneWithEagerRelationships(@Param("id") Long id);

}
