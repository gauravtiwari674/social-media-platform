
package com.socialmedia.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "`Groups`")
public class SocialGroup { // Renamed from'Group' as it is a reserved word in Java/JPA sometimes, but table name is Groups

    @Id
    @Column(name = "groupID")
    private int groupID;

    @Column(name = "groupName")
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "adminID", nullable = false)
    private User admin;

    // Default Constructor
    public SocialGroup() {
    }

    // Parameterized Constructor
    public SocialGroup(int groupID, String groupName, User admin) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.admin = admin;
    }

    // Getters and Setters
    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }
}
