package com.oui.integersets.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(
        name = "set_member"
)

public class SetMember {

    public static final String SET_MEMBER_ID = "set_member_id";
    public static final String SET_ID = "set_id";
    public static final String SET_MEMBER_ENTRY_NUM = "set_member_entry_num";
    public static final String SET_MEMBER_VALUE = "set_member_value";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = SET_MEMBER_ID,
            updatable = false,
            nullable = false
    )
    private Long setMemberId;

    @Column(
            name = SET_ID,
            nullable = false
    )
    private int setId;


    @Column(
            name = SET_MEMBER_ENTRY_NUM,
            nullable = false
    )
    private int setMemberEntryNum;


    @Column(
            name = SET_MEMBER_VALUE,
            nullable = false
    )
    private int setMemberValue;

    public SetMember() {
    }

    public SetMember(Long setMemberId, int setId, int setMemberEntryNum, int setMemberValue) {
        this.setMemberId = setMemberId;
        this.setId = setId;
        this.setMemberEntryNum = setMemberEntryNum;
        this.setMemberValue = setMemberValue;
    }

    public Long getSetMemberId() {
        return setMemberId;
    }

    public void setSetMemberId(Long setMemberId) {
        this.setMemberId = setMemberId;
    }

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    public int getSetMemberEntryNum() {
        return setMemberEntryNum;
    }

    public void setSetMemberEntryNum(int setMemberEntryNum) {
        this.setMemberEntryNum = setMemberEntryNum;
    }

    public int getSetMemberValue() {
        return setMemberValue;
    }

    public void setSetMemberValue(int setMemberValue) {
        this.setMemberValue = setMemberValue;
    }
}
