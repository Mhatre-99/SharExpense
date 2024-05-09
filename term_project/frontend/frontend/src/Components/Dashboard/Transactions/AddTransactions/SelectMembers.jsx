import React, {useEffect, useState} from 'react';
import Select from 'react-select';
import "./transaction.css";
import api from "../../../../baseUrl";
import {useLocation} from "react-router-dom";
import authHeader from "../../../../Services/DataService";
import {forEach} from "react-bootstrap/ElementChildren";

const SelectMembers = ({ onMembersChange, selectedMembers }) => {

    const location = useLocation();
    const {group} = location.state;
    const [members, setMembers] = useState([{ value: 'everyone', label: 'Everyone' }]);
    /*const members = [
        { value: 'member1', label: 'Member 1' },
        { value: 'member2', label: 'Member 2' },
        { value: 'member3', label: 'Member 3' },
        { value: 'member4', label: 'Member 4' },
    ];*/

    const group_id = {group}.group.id;
    useEffect(() => {

        api.get(`group-user/${group_id}`, {
            headers: authHeader()
        }).then(res => {
            const response = res.data;
            const updatedMembers = response.map(item => ({
                value: item,
                label: item
            })).concat(members);
            setMembers(updatedMembers);
            console.log("GROUPS ", members);
        });
        }, [group_id]);


    const handleChange = selectedOptions => {
        const isEveryoneSelected = selectedOptions.some(option => option.value === 'everyone');
        if (isEveryoneSelected) {
            onMembersChange([{ value: 'everyone', label: 'Everyone' }]);
        } else {
            onMembersChange(selectedOptions || []);
        }
    };

    return (
        <div className="select-members-container">
            <Select
                isMulti
                name="members"
                options={members}
                className="basic-multi-select"
                classNamePrefix="select"
                onChange={handleChange}
                value={selectedMembers}
            />
        </div>
    );
};

export default SelectMembers;
