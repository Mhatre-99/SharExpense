import React, {useEffect, useState} from 'react';
import UploadFile from "./UploadFIle";
import {useLocation} from "react-router-dom";
import PerItem from "./PerItem";
import "./transaction.css";
import Button from "react-bootstrap/Button";
export default function AddTransactions(){
    const location = useLocation();
    const {username} = location.state;
    const {group} = location.state;

    const [data , setData] = useState([]);

    useEffect(() => {
        console.log("Updated data in AddTransactions: ", data);
    }, [data]);
    const onDataUpdate = (items) => {
        console.log("DATA received ", items)
        setData(items);
    }



    console.log("username in addtransactions ", username)
    return (
        <>
            <UploadFile className="upload" onGettingItems = {onDataUpdate} username={username}/>
            <PerItem items = {data} group={group} username = {username}/>
        </>
    )
}