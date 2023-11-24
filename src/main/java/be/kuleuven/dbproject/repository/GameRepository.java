package be.kuleuven.dbproject.repository;

import be.kuleuven.dbproject.model.Game;

import javax.persistence.EntityManager;

public class GameRepository extends EntityRepository<Game> {
    public GameRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Game> getEntityClass() {
        return Game.class;
    }
}
