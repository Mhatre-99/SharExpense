import React from 'react';
import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';


function Navigation() {
    return (
        <Navbar className='nav-bar'>
            <Container>
                <Navbar.Brand className="brand-name">SharExpense </Navbar.Brand>

            </Container>

        </Navbar>
    );
}

export default Navigation;