package com.oui.integersets.repository.impl;

import com.oui.integersets.model.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.stream.Collectors;


@Repository
public class SetMemberRepositoryImpl {

    @Autowired
    protected EntityManager entityManager;

    /**
     *  Takes in a set object and retrieves all setId's from the index (setMember) that have at least one of
     *  the same members.
     * @param set
     * @return
     */
    public java.util.Set<Integer> getIntersectingSetIds(Set set) {
        TypedQuery<Integer> query = entityManager.createQuery("select s.setId " +
                "from SetMember s " +
                "where s.setMemberValue in :members " +
                "and s.setId != :setId", Integer.class)
                .setParameter("members", set.getMembers())
                .setParameter("setId", set.getSetId());

        java.util.Set<Integer> intersectingSetIds = query.getResultList().stream().collect(Collectors.toSet());
        return intersectingSetIds;
    }


}
