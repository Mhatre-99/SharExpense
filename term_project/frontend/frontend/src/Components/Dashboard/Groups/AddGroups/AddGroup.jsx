import React, {useEffect, useState} from 'react';
import { Button, TextField, IconButton } from '@mui/material';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline';
import Container from "react-bootstrap/Container";
import {useLocation, useNavigate} from "react-router-dom";
import CheckUsername from "./CheckUsername";
import api from "../../../../baseUrl";
import authHeader from "../../../../Services/DataService";
export default function DynamicForm() {
    const location  = useLocation();
    const [inputs, setInputs] = useState(['']);
    const [Gname, setGname] = useState("");
    const [canSubmit , setCanSubmit] = useState(false);
    const {username} = location.state.username;
    const navigate = useNavigate();

    useEffect(() => {
        const validateAllUsernames = async () => {
            const allUsernamesValid = await Promise.all(inputs.map(async (username) => {
                if (!username) return false;
                return await CheckUsername(username);
            }));
            setCanSubmit(allUsernamesValid.every(isValid => isValid));
        };

        validateAllUsernames();
    }, [inputs]);

    const checkLastUsername = async () => {
        const lastInput = inputs[inputs.length - 1];
        if (lastInput) {
            const isAvailable = await CheckUsername(lastInput);
            return isAvailable;
        }
        return true;
    };

    const handleAddInput = async () => {
        setCanSubmit(false);
        const isLastUsernameAvailable = await checkLastUsername();
        if (isLastUsernameAvailable && !inputs.includes('')) {
            setInputs([...inputs, '']); // Add new input field only if the last username is available
            setCanSubmit(true);
        } else {
            alert('Please ensure the last username is available and unique before adding another.');
            setCanSubmit(false);
        }
    };

    const handleRemoveInput = (indexToRemove) => {
        setInputs(inputs.filter((_, index) => index !== indexToRemove));
    };
    const addGroup = () => {
        inputs.push(username)
        if(Gname === ""){
            setGname("group");
        }
        try{api.post("/add-group",{
            groupName: Gname,
            createdBy: username,
            members: inputs
        }, {
            headers: authHeader()
        }).then(res => {
            console.log("after post ",res)
        })
    } catch (error){
            console.log("error ", error);
            alert(error)

        }}
    const handleInputChange = (index, event) => {
        const values = [...inputs];
        values[index] = event.target.value;

        setInputs(values);
    };

    const handleSubmit = (event) => {
        const isLastUsernameAvailable = checkLastUsername();
        if (isLastUsernameAvailable && !inputs.includes('')) {
            event.preventDefault();
            //console.log(inputs);
            addGroup();
            const previousUrl = document.referrer; // This gets the URL of the previous page
            console.log("previousUrl ", previousUrl)
            navigate(-1);
        }else{
            alert("please enter a username");
            setCanSubmit(false);
            return null;
        }
    };

    return (
        <Container className="addGroup">
        <form >
            <label htmlFor="Gname" style={{'margin-bottom':'1%', 'font-size':'calc(10px + 5vmin)'}}>Group Name</label><br/>
            <input id =" Gname" type="text" value={Gname} style={{'margin-bottom':'1%', 'width':'222.4px'}} onChange={(e) => setGname(e.target.value)} placeholder="Name of the Group"/><br/>
            <label style={{'margin-bottom':'1%', 'font-size':'calc(10px + 5vmin)'}}>Member Name</label><br/>
            {inputs.map((input, index) => (
                <div key={index}>
                    <TextField
                        type="text"
                        value={input}
                        placeholder="Add members username"
                        onChange={(event) => handleInputChange(index, event)}

                        style={{ margin: '10px 0' }}
                    />
                    <IconButton onClick={() => handleRemoveInput(index)} color="primary" aria-label="remove input">
                        <RemoveCircleOutlineIcon />
                    </IconButton>
                </div>
            ))}
            <IconButton onClick={handleAddInput} color="primary" aria-label="add input">
                <AddCircleOutlineIcon />
            </IconButton>
            <Button  type="submit" onClick={handleSubmit} variant="contained" color="primary" disabled={!canSubmit}>Submit</Button>
        </form>
        </Container>
    );
}
