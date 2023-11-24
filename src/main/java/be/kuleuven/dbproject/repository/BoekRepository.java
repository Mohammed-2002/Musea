package be.kuleuven.dbproject.repository;

import be.kuleuven.dbproject.model.Boek;

import javax.persistence.EntityManager;

public class BoekRepository extends EntityRepository<Boek>{
    public BoekRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Boek> getEntityClass() {
        return Boek.class;
    }
}
