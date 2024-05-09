import React, {useEffect, useState} from 'react';
import api from "../../../../baseUrl";
import {useLocation, useParams} from "react-router-dom";
import authHeader from "../../../../Services/DataService";
import Container from "react-bootstrap/Container";

export default function UploadFile({username, onGettingItems}){

    const [receipt, setReceipt] = useState();
    const location = useLocation();
    const {id} = useParams();
    const [response, setResponse] = useState([]);

    console.log(username);
    function handleOnChange(e){
        setReceipt(e.target.files[0]);
    }

    const handleUpload = ()=> {
        if (!receipt) {
            alert('Please select a file first!');
            return;
        }
        const formData = new FormData();
        formData.append('file', receipt);
        formData.append('id', id);
        formData.append('username', username);

        const auth = authHeader();


            api.post("/parse-file", formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    'Authorization': auth.Authorization
                },
            }).then(res => {
                    console.log('File successfully uploaded ', res.data.items)
                    setResponse(res.data.items);
                }
            )
                .catch(error => console.error('Error uploading file', error));



    }

    useEffect(() => {
        console.log("response in useeffect ", response)
        onGettingItems(response);
    }, [response]);

    return (
        <Container className="upload">
            <input type='file' onChange={handleOnChange} accept=".jpg,.png,.jpeg"/>
            <button type="submit" onClick={handleUpload}>Upload</button>
        </Container>
    )
}