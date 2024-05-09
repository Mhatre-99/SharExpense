
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import {Col} from "react-bootstrap";
import card_image from "../../Navbar/const/Frame.png";
import {useLocation, useNavigate} from "react-router-dom";
import React from 'react';

function GroupCard({group , onGroupClick}) {

    return (
        <Col>
        <Card>
            <Card.Img variant="top" src={card_image} />
            <Card.Body>
                <Card.Title>{group.name}</Card.Title>
                <Card.Text>
                    {group.amountType} {group.amount.toFixed(2)}
                </Card.Text>
                <Button variant="primary" onClick={()=>onGroupClick(group.id)}>Go in</Button>
            </Card.Body>
        </Card>
        </Col>
    );
}

export default GroupCard;