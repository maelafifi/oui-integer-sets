package com.oui.integersets.repository;

import com.oui.integersets.model.SetMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetMemberRepository extends JpaRepository<SetMember, Integer> {

}
