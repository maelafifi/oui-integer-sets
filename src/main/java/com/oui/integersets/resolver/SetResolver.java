package com.oui.integersets.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.oui.integersets.repository.SetRepository;
import com.oui.integersets.model.Set;
import com.oui.integersets.repository.impl.SetMemberRepositoryImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SetResolver implements GraphQLResolver<Set> {
    private SetRepository setRepository;
    private SetMemberRepositoryImpl setMemberRepositoryImpl;

    public SetResolver(SetRepository setRepository, SetMemberRepositoryImpl setMemberRepositoryImpl) {
        this.setRepository = setRepository;
        this.setMemberRepositoryImpl = setMemberRepositoryImpl;
    }

    public List<Set> getIntersectingSets(Set set) {
        java.util.Set<Integer> intersectingSetIds =  setMemberRepositoryImpl.getIntersectingSetIds(set);
        List<Set> intersectingSets = setRepository.findAllById(intersectingSetIds);
        return intersectingSets;
    }
}
