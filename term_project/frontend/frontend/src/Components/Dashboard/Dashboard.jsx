import Navbar from "../Navbar/Navbar";
import Grid from "@mui/material/Grid";
import {Route, Routes, Outlet} from "react-router-dom";
import ExpenseGroups from "./Groups/ExpenseGroups";
import Container from "react-bootstrap/Container";
import React from 'react';
import NotFound from "../Protected/NotFound";
import Group from "./Groups/Group";
import DynamicForm from "./Groups/AddGroups/AddGroup";
import AddTransactions from "./Transactions/AddTransactions/AddTransactions";


export default function Dashboard(){
    return (
        <div>

        <Grid container >
            <Grid item sx={{flex:0.2}} >
                <Navbar />
              </Grid>
            <Grid item sx={{flex:0.8}}>
                <Container className="dashboard-navbar">
                    <div>SharExpense</div>
                </Container>
                <Routes>
                    <Route path="/groups/:username" element={<ExpenseGroups/>}>
                        <Route path="add-group" element={<DynamicForm/>}/>
                    </Route>
                    <Route path="/group/:id/*" element={<Group/>}/>
                    <Route path="*" element={<NotFound />}/>
                </Routes>
            </Grid>
        </Grid>
        </div>
            )
}
