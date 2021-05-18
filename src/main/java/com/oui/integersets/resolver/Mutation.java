package com.oui.integersets.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.oui.integersets.exception.BadRequestException;
import com.oui.integersets.model.SetMember;
import com.oui.integersets.repository.SetMemberRepository;
import com.oui.integersets.repository.SetRepository;
import com.oui.integersets.model.Set;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class Mutation implements GraphQLMutationResolver {

    private SetRepository setRepository;
    private SetMemberRepository setMemberRepository;

    public Mutation(SetRepository setRepository, SetMemberRepository setMemberRepository) {
        this.setRepository = setRepository;
        this.setMemberRepository = setMemberRepository;
    }

    public Set createSet(Object membersObject) {

        // this is stupid and could have been avoided if I implemented the graphql resolver to handle controller input
        String membersString = membersObject.toString();
        membersString = membersString.replaceAll("\\s", "");
        membersString = membersString.replaceAll(".+\\[", "");
        membersString = membersString.replaceAll("].*", "");
        String[] memberArray = membersString.split(",");

        List<Integer> members = new ArrayList<>();

        for(String s : memberArray) {
            members.add(Integer.valueOf(s));
        }

        HashSet<Integer> memberSet = new HashSet<>();

        for(Integer s : members) {
            memberSet.add(s);
        }

        // TODO: throw error due to duplicate values in set
        if(memberSet.size() != members.size()) {
            throw new BadRequestException(String.format("Duplicate integers in member set: %s", members.toString()));
        }

        // create a new Set domain object
        Set set = new Set();
        set.setMembers(members);
        setRepository.save(set);


        int setId = set.getSetId();
        int currEntryNum = 1;
        for( ; currEntryNum < members.size(); currEntryNum++) {
            SetMember setMember = new SetMember();
            setMember.setSetId(setId);
            setMember.setSetMemberEntryNum(currEntryNum);
            setMember.setSetMemberValue(members.get(currEntryNum));
            setMemberRepository.save(setMember);
        }

        return set;
    }



}
