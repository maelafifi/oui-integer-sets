package com.oui.integersets.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.oui.integersets.repository.SetMemberRepository;
import com.oui.integersets.repository.SetRepository;
import com.oui.integersets.model.Set;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {

    private SetRepository setRepository;
    private SetMemberRepository setMemberRepository;

    public Query(SetRepository setRepository, SetMemberRepository setMemberRepository) {
        this.setRepository = setRepository;
        this.setMemberRepository = setMemberRepository;
    }

    // returns list of all set objects
    public Iterable<Set> getSets() {
        return setRepository.findAll();
    }

}
