package com.oui.integersets.model;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(
        name = "set"
)
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)

public class Set {

    public static final String SET_ID = "set_id";
    public static final String SET_MEMBERS = "set_members";
    public static final String SET_UNIQUE_ID = "set_unique_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = SET_ID,
            updatable = false,
            nullable = false
    )
    private int setId;

    @Type( type = "list-array" )
    @Column(
            name = SET_MEMBERS,
            updatable = false
    )
    private List<Integer> setMembers;

    @Column(
            name = SET_UNIQUE_ID,
            updatable = false
    )
    private String setUniqueId;

    public Set() {
    }

    public Set(List<Integer> setMembers) {
        this.setMembers = setMembers;
    }

    public Set(int setId, List<Integer> setMembers) {
        this.setId = setId;
        this.setMembers = setMembers;
    }

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    public List<Integer> getMembers() {
        return setMembers;
    }

    public void setMembers(List<Integer> setMembers) {
        this.setMembers = setMembers;
    }

    public String getSetUniqueId() {
        return setUniqueId;
    }

    public void setSetUniqueId(String setUniqueId) {
        this.setUniqueId = setUniqueId;
    }
}
