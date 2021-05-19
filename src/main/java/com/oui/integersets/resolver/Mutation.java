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
import java.util.stream.Collectors;

@Component
public class Mutation implements GraphQLMutationResolver {

    private SetRepository setRepository;
    private SetMemberRepository setMemberRepository;

    public Mutation(SetRepository setRepository, SetMemberRepository setMemberRepository) {
        this.setRepository = setRepository;
        this.setMemberRepository = setMemberRepository;
    }

    /**
     * Takes in a membersObject, parses it into a set member List, persists to the set tabe
     * then continues to persist each individual member of the set to the setMember table
     * @param membersObject
     * @return
     */
    public Set createSet(Object membersObject) {

        List<Integer> members = memberMapper(membersObject);

        if(!checkDuplicates(members)) {
            throw new BadRequestException(String.format("Duplicate integers in member set: %s", members.toString()));
        }

        // create a new Set domain object
        // TODO: WRAP IN TRY-CATCH
        Set set = new Set();
        set.setMembers(members);
        setRepository.save(set);

        // create a new setMember object for each memeber of the set
        int setId = set.getSetId();
        int currEntryNum = 1;
        for( ; currEntryNum < members.size(); currEntryNum++) {
            SetMember setMember = new SetMember();
            setMember.setSetId(setId);
            setMember.setSetMemberEntryNum(currEntryNum);
            setMember.setSetMemberValue(members.get(currEntryNum));
            // TODO: WRAP IN TRY-CATCH
            setMemberRepository.save(setMember);
        }

        return set;
    }


    /**
     * Checks to ensure that sets with duplicate values aren't being
     * @param members
     * @return
     */
    private Boolean checkDuplicates(List<Integer> members) {
        java.util.Set<Integer> memberSet = members.stream().collect(Collectors.toSet());

        if(memberSet.size() != members.size()) {
            return false;
        }
        return true;
    }

    // this is stupid and could have been avoided if I implemented the graphql resolver to handle controller input
    private List<Integer> memberMapper(Object membersObject) {
        String membersString = membersObject.toString();
        membersString = membersString.replaceAll("\\s", "");
        membersString = membersString.replaceAll(".+\\[", "");
        membersString = membersString.replaceAll("].*", "");

        String[] memberArray = membersString.split(",");

        List<Integer> members = new ArrayList<>();

        for(String s : memberArray) {
            members.add(Integer.valueOf(s));
        }
        return members;
    }





}
