package be.kuleuven.dbproject.repository;

import be.kuleuven.dbproject.model.Museum;

import javax.persistence.EntityManager;

public class MuseumRepository extends EntityRepository<Museum> {

    public MuseumRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Museum> getEntityClass() {
        return Museum.class;
    }
}
