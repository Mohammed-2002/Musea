package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.model.Medewerker;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class SharedData {
    private static SharedData instance;

    private Medewerker loggedInMedewerker;
    private EntityManager entityManager;

    private SharedData() {
           var sessionFactory = Persistence.createEntityManagerFactory("be.kuleuven.dbproject");
           this.entityManager = sessionFactory.createEntityManager();
    }

    public static SharedData getInstance() {
        if (instance == null) {
            instance = new SharedData();
        }
        return instance;
    }

    public Medewerker getLoggedInMedewerker() {
        return loggedInMedewerker;
    }

    public void setLoggedInMedewerker(Medewerker medewerker) {
        this.loggedInMedewerker = medewerker;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}

