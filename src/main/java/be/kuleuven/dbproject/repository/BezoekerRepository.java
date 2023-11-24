package be.kuleuven.dbproject.repository;

import be.kuleuven.dbproject.model.Bezoeker;

import javax.persistence.EntityManager;

public class BezoekerRepository extends EntityRepository<Bezoeker>{

    public BezoekerRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Bezoeker> getEntityClass() {
        return Bezoeker.class;
    }
}
