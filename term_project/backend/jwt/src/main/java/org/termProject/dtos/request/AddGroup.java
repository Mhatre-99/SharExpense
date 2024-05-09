package org.termProject.dtos.request;

import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

public class AddGroup {

    @NotBlank
    private String groupName;

    @NotBlank
    private String createdBy;
    @NotBlank
    private HashSet<String> members;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<String> getMembers() {
        return members;
    }

    public void setMembers(HashSet<String> members) {
        this.members = members;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
