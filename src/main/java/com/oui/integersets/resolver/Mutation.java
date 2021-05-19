package com.oui.integersets.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.oui.integersets.exception.BadRequestException;
import com.oui.integersets.model.SetMember;
import com.oui.integersets.repository.SetMemberRepository;
import com.oui.integersets.repository.SetRepository;
import com.oui.integersets.model.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.security.MessageDigest;

@Component
public class Mutation implements GraphQLMutationResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Mutation.class);

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
    @Transactional
    public Set createSet(Object membersObject) {

        List<Integer> members = memberMapper(membersObject);

        if(!checkDuplicates(members)) {
            LOGGER.error("Received request with duplicate values; rejecting");
            throw new BadRequestException(String.format("Duplicate integers in member set: %s", members.toString()));
        }

        String setUniqueId = generateUniqueIdentifier(members.toString());

        // create a new Set object
        Set set = new Set();
        set.setMembers(members);
        set.setSetUniqueId(setUniqueId);
        try {
            LOGGER.info(String.format("Attempting to create set: %s", members.toString()));
            setRepository.save(set);
            LOGGER.info(String.format("Set created with ID: %s", set.getSetId()));
        } catch(Exception e) {
            LOGGER.error(String.format("Error persisting set: %s ; Error: %s", members.toString(), e.getLocalizedMessage()));
            throw new BadRequestException(e.getMessage());
        }

        // create a new setMember object for each member of the set
        int setId = set.getSetId();
        int currEntryNum = 1;
        for( ; currEntryNum < members.size(); currEntryNum++) {
            SetMember setMember = new SetMember();
            setMember.setSetId(setId);
            setMember.setSetMemberEntryNum(currEntryNum);
            setMember.setSetMemberValue(members.get(currEntryNum));
            try {
                LOGGER.info(String.format("Attempting to persist member %s (%s) of set", currEntryNum, members.get(currEntryNum)));
                setMemberRepository.save(setMember);
            } catch(Exception e) {
                LOGGER.error(String.format("Error persisting member: %s ; Error: %s", members.toString(), e.getLocalizedMessage()));
                throw new BadRequestException(e.getMessage());
            }
        }

        return set;
    }


    /**
     * Helper method
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

    // Helper; this is stupid and could have been avoided if I implemented the graphql resolver to handle controller input
    // Basically, this consumes and parses the members object ({members=[1, 2, 3, 4]}), which is passed along from the
    // graphql built-in methods since I didn't implement the controller layer. Had I gone the other route, I could have
    // parsed this object to a pojo (which somewhat seems antithetical) and below could have bee avoided altogether
    private List<Integer> memberMapper(Object membersObject) {
        String membersString = membersObject.toString();
        membersString = membersString.replaceAll("\\s", "");
        membersString = membersString.replaceAll(".+\\[", "");
        membersString = membersString.replaceAll("].*", "");

        String[] memberArray = membersString.split(",");

        List<Integer> members = new ArrayList<>();


        // handle the empty set scenario
        if(membersString.length() == 0) {
            return members;
        }


        for(String currInteger : memberArray) {
            try {
                members.add(Integer.valueOf(currInteger));
            } catch(Exception e) {
                throw new BadRequestException(String.format("Member %s of set cannot be parsed to an integer", currInteger));
            }
        }
        return members;
    }

    /**
     * Helper; Takes in a string, hashes the string, returns the hashed string. Can be used as a unique identifier.
     * Arbitrarily chose md2. Stolen from https://www.geeksforgeeks.org/md2-hash-in-java/
     * @param membersString
     * @return
     */
    private String generateUniqueIdentifier(String membersString) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            byte[] messageDigest = md.digest(membersString.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
