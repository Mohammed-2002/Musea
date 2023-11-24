package be.kuleuven.dbproject.repository;

import be.kuleuven.dbproject.model.Medewerker;

import javax.persistence.EntityManager;

public class MedewerkerRepository extends EntityRepository<Medewerker>{

    public MedewerkerRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Medewerker> getEntityClass() {
        return Medewerker.class;
    }
}
