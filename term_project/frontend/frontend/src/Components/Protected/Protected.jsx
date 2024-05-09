import React, {useEffect} from 'react';
import api from "../../baseUrl";
import {Route, Routes, useNavigate} from "react-router-dom";
import Dashboard from "../Dashboard/Dashboard";
import NotFound from "./NotFound";
export default function Protected(){

    const token = localStorage.getItem("user");
    console.log(token);
    const navigate = useNavigate();
    useEffect(() => {
        if (!token) {
            navigate('/login');
        }
    }, [token, navigate]); // Adding `navigate` as a dependency is optional but ensures the linter is happy.

    return (
        <Routes>
            <Route path="/dashboard/*" element={<Dashboard />} />
            <Route path="*" element={<NotFound />}/>
        </Routes>
    );
}