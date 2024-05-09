import Transaction from "../Transactions/Transactions";
import Container from "react-bootstrap/Container";
import IconButton from "@mui/material/IconButton";
import AddIcon from "@mui/icons-material/Add";
import {Route, Routes, useLocation, useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from 'react';
import api from "../../../baseUrl";
import authHeader from "../../../Services/DataService";
import AddTransactions from "../Transactions/AddTransactions/AddTransactions";
import Balances from "./balances";
import Button from "react-bootstrap/Button";
export default function Group() {

    const location = useLocation();
    const {id} = useParams();
    const navigate = useNavigate();
    const [group, setGroup] = useState({});
    const {username} = location.state;
    console.log("group ", username);
    const hideButton = location.pathname.includes("/add-transaction");
    const [balances,setBalances] = useState([]);
    const [payback , setPayback] = useState(false);
    function handleOnClickAddGroup() {
        navigate(location.pathname+"/add-transaction".replace("//",'/'),{ state: { username: username , group: {id}} });
    }


    function handlePayOnClick(){
        const transactionData = {
            groupId: {id}.id,
            name: "payment",
            initiatedBy: username,
            receiptName: null,
            type: "payback",
            subTransactions:null
        }
        console.log(transactionData)
        api.post("/create-transaction", transactionData,{
            headers:authHeader()
        }).then(res => {
            const response = res.data;
            console.log("response of create transaction ", response);
            navigate(-1);
        }).catch(error => {
            console.log("error creating transaction ", error)
        })
    }

    useEffect(() => {
        api.get(`/group/${id}`,{
            headers:authHeader()
        }).then(res => {
            console.log(res.data);
            setGroup(res.data);
            const balances = res.data.balances;
            setBalances(balances);
            balances.forEach((b)=>{
                console.log("BALANCE "+b);
                console.log(b.trim().split(/\s+/)[0]);
                if(b.trim().split(/\s+/)[0] === username){

                    setPayback(true);
                }
            })
        })

    }, [id]);


    return(
        <Container className="dashboard-title">
            <div className= "title">
                <span>{group.name}</span>
                {!hideButton &&
                <span>
                    <IconButton
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
                </span>}
            </div>

            {!hideButton &&
                <div>
                    {balances.map((balance,index)=>(
                        <Balances balance={balance}/>
                    ))}
                </div>}
            {!hideButton &&
                <div>
                    <Button type="submit" onClick={handlePayOnClick} disabled={!payback}>Pay Balance</Button>
                </div>}
            <Routes>
                <Route path="/" element={group.transactionsDTOS && group.transactionsDTOS.map((transaction) => (
                    <Transaction key={transaction.id} transactionData={transaction} />
                ))} />
                <Route path="add-transaction" element={<AddTransactions username={username}/>}/>
            </Routes>


        </Container>
    )
}