import Container from "react-bootstrap/Container";
import {Row} from "react-bootstrap";
import GroupCard from "./GroupCard";
import IconButton from '@mui/material/IconButton';
import AddIcon from '@mui/icons-material/Add';
import {useNavigate, useLocation, Route, Routes, useParams, Outlet} from "react-router-dom";
import React, {useEffect, useState} from 'react';
import DynamicForm from "./AddGroups/AddGroup";
import Group from "./Group";
import api from "../../../baseUrl";
import authHeader from "../../../Services/DataService";
import NotFound from "../../Protected/NotFound";

export default function ExpenseGroups (){
    const {username} = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    const [groups, setGroups] = useState([]);


    useEffect(() => {
        api.get(`/groups`,{
            headers: authHeader()
        }).then(res => {
            setGroups( res.data);
            console.log("GROUPS ", groups);
        })
    }, []);


    console.log("username in expense group ", {username})

    function handleOnClickAddGroup() {
        navigate("add-group", {state : {"username":{username}}});
    }

    const showingAddGroup = location.pathname.includes('/add-group');
    if (showingAddGroup) {
        // Directly return to prevent rendering other components
        return <DynamicForm />;
    }

    const handleOnGroupClick = (groupId) => {
        console.log("Group button clicked ",groupId );
        navigate(`/access/dashboard/group/${groupId}`,{
            state:{
                username:username
            }
        });
    }

    return (
        <Container className="dashboard-title">

                <div className="title">
                <span>Groups</span>
                <span>  <IconButton
                    onClick={handleOnClickAddGroup}
                    color="secondary"
                    backgroundColor="red"
                    aria-label="add"
                    sx={{
                        '&:hover': {
                            backgroundColor: 'rgba(0, 0, 0, 0.8)', // Example darker color on hover
                        },

                        border: '2px solid #fff',
                        borderRadius: '50%',
                        width: 56,
                        height: 56,
                    }}
                >
                    <AddIcon fontSize="large" />
                </IconButton>
                </span>
                </div>

            <Row xs={1} md={3} lg={4} className="g-4">
                {groups.map((group) => (
                    <GroupCard key={group.id} group={group} username={username} onGroupClick={handleOnGroupClick}/>
                ))}
            </Row>
        </Container>
    )
}