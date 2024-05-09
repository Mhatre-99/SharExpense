import React from 'react';
import Button from '@mui/material/Button';
import Grid from "@mui/material/Grid";
import {Container} from "@mui/material";
import Login from "./Login";
import RegisterForm from "./SignUp";
import NavigationBar from "../Navbar/NavigationBar";
import {useLocation} from "react-router-dom";
export default function Authentication() {
    const location = useLocation();
    return (
        <>
            <NavigationBar />
            <Container>
                {location.pathname === "/login" ? (
                    <Login />
                ) : (
                    <RegisterForm />
                )}
            </Container>
        </>
    );
}