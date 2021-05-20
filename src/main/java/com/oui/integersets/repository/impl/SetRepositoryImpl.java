package com.oui.integersets.repository.impl;

import com.oui.integersets.model.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;


@Repository
public class SetRepositoryImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetRepositoryImpl.class);
    @Autowired
    protected EntityManager entityManager;

    /**
     * Checks to see if a unique identifier exists for a particular set; returns true if one exists, false if it doesn't
     * @param setUniqueId
     * @return
     */
    public Boolean getSetExists(String setUniqueId) {
        TypedQuery<Set> query = entityManager.createQuery("from Set s " +
                "where s.setUniqueId = :setUniqueId ", Set.class)
                .setParameter("setUniqueId", setUniqueId);

        try {
            query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.info("Set does not exist.");
            return false;
        }
        return true;
    }


}
