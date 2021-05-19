package com.oui.integersets.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.oui.integersets.repository.SetRepository;
import com.oui.integersets.model.Set;
import com.oui.integersets.repository.impl.SetMemberRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SetResolver implements GraphQLResolver<Set> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SetResolver.class);

    private SetRepository setRepository;
    private SetMemberRepositoryImpl setMemberRepositoryImpl;

    public SetResolver(SetRepository setRepository, SetMemberRepositoryImpl setMemberRepositoryImpl) {
        this.setRepository = setRepository;
        this.setMemberRepositoryImpl = setMemberRepositoryImpl;
    }


    /**
     * Takes in a set object and retrieves all setId's from the index (setMember) that have at least one of
     * the same members. it then retrieves the list of set objects based on the id's gather in hte first partt
     * @param set
     * @return a list of Set objects, containing setId and their setMembers
     */
    public List<Set> getIntersectingSets(Set set) {
        // handle empty set
        if(set.getMembers().size() == 0) {
            return new ArrayList<>();
        }

        LOGGER.info(String.format("Retrieving list of sets that intersect %s", set.getMembers()));
        java.util.Set<Integer> intersectingSetIds =  setMemberRepositoryImpl.getIntersectingSetIds(set);
        List<Set> intersectingSets = setRepository.findAllById(intersectingSetIds);
        return intersectingSets;
    }
}
