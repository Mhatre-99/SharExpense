import React, {useEffect, useState} from 'react';
import { Button, Form, Container, Row, Col } from 'react-bootstrap';
import SelectMembers from "./SelectMembers";
import api from "../../../../baseUrl"
import authHeader from "../../../../Services/DataService";
import {useNavigate} from "react-router-dom";

const initialRow = { itemName: '', itemPrice: '', membersInvolved: [] };

const PerItem = ({items,username, group}) => {
    const navigate = useNavigate();
    const [rows, setRows] = useState([initialRow]);
    const [selectedMembers, setSelectedMembers] = useState([]);
    const [transactionName, setTransactionName] = useState();

    useEffect(() => {

        if (items && items.length > 0) {
            const newRows = items.map(item => ({
                itemName: item.itemName || '',
                itemPrice: item.itemPrice || '',
                membersInvolved: item.membersInvolved || [],
            }));
            setRows(newRows);
        }
    }, [items]);
    const handleRowChange = (index, event) => {
        const updatedRows = [...rows];
        if (event.target) { // Regular input fields
            const { name, value } = event.target;
            updatedRows[index][name] = value;
        }
        setRows(updatedRows);
    };

    const addRow = () => {
        const newRow = { itemName: '', itemPrice: '', membersInvolved: [] };
        setRows(rows => [...rows, newRow]);
    };

    const removeRow = (index) => {
        const updatedRows = [...rows];
        updatedRows.splice(index, 1);
        setRows(updatedRows);
    };

    const handleMembersChange = (index, selectedOptions) => {
        const memberValues = selectedOptions.map(option => option.value);
        const updatedRows = [...rows];
        updatedRows[index].membersInvolved = memberValues;
        setRows(updatedRows);
    };



    const handleOnSubmit= () => {
        console.log(rows)
        if (transactionName === ""){
            setTransactionName( "transaction")
        }
        const transactionData = {
            groupId: {group}.group.id,
            name: transactionName,
            initiatedBy: {username}.username,
            receiptName: "receipt.jpeg",
            type: "lent",
            subTransactions:rows
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

    const handleName = (e)=>{
        setTransactionName(e.target.value);
    }


    return (
        <Container className="per-item">
            <input type="text" onChange={handleName} placeholder={"Transaction Name"} style={{"margin-bottom":"2%"}}/>
            {rows.map((row, index) => (
                <Row className="mb-3" key={index}>
                    <Col>
                        <Form.Control
                            type="text"
                            name="itemName"
                            value={row.itemName}
                            onChange={(e) => handleRowChange(index, e)}
                            placeholder="Product Name"
                        />
                    </Col>
                    <Col>
                        <Form.Control
                            type="number"
                            name="itemPrice"
                            value={row.itemPrice}
                            onChange={(e) => handleRowChange(index, e)}
                            placeholder="Amount"
                        />
                    </Col>
                    <Col>
                        <SelectMembers  selectedMembers={row.membersInvolved.map(value => ({ value, label: value }))}
                                        onMembersChange={(selectedOptions) => handleMembersChange(index, selectedOptions)}/>
                    </Col>
                    <Col xs="auto">
                        <Button variant="danger" onClick={() => removeRow(index)}>Remove</Button>
                    </Col>
                </Row>
            ))}
            <Button onClick={addRow}>Add Expense Item</Button>
            <div>
            <Button className="submitTransaction" type="submit" onClick={handleOnSubmit}>Submit</Button>
            </div>
            </Container>
    );
};

export default PerItem;
