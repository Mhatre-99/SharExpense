package org.termProject.dtos.response;

import org.termProject.dtos.SEGroupsDTO;

import java.util.List;

public class GroupDTO {
    private SEGroupsDTO segroup;
    private List<String> usernames;

    public GroupDTO(SEGroupsDTO segroup, List<String> usernames){
        this.segroup = segroup;
        this.usernames = usernames;
    }

    public SEGroupsDTO getSegroup() {
        return segroup;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setSegroup(SEGroupsDTO segroup) {
        this.segroup = segroup;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
}
