package com.oui.integersets.repository;

import com.oui.integersets.model.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetRepository extends JpaRepository<Set, Integer> {
}
