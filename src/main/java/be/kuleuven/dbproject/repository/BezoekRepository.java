package be.kuleuven.dbproject.repository;

import be.kuleuven.dbproject.model.Bezoek;

import javax.persistence.EntityManager;

public class BezoekRepository extends EntityRepository<Bezoek> {
    public BezoekRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Bezoek> getEntityClass() {
        return Bezoek.class;
    }
}
