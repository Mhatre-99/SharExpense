import React from "react";
import Container from "react-bootstrap/Container";
import {Col, Row} from "react-bootstrap";
import logo from "../../Navbar/const/Frame.png";
import "./transactions.css"

export default function Transaction({transactionData}) {
    return (
        <Container className="transaction">
        <Row className="justify-content-center video-row">
            <Col sm={3}>

                <img src={logo} alt="thumbnail" className="thumbnail"></img>

            </Col>

    <Col >
        <div className="transaction-name">
            {transactionData.name}
        </div>
        <div className="paidby">
            {transactionData.initiatedBy} paid {transactionData.amount}
        </div>
        <div>
            <Row>
                <Col className="transaction-date">date</Col>
                <Col className="balance-status"> {transactionData.transactionType}</Col>
                <Col className="balance">{transactionData.amount}</Col>
            </Row>
        </div>

    </Col>
</Row>
</Container>
    )
}