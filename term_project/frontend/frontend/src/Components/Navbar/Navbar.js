import  React from 'react';
import Drawer from '@mui/material/Drawer';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';

import { navbarStyles } from './const/navbarConst';
import {mainNavbarItems} from "./const/navbarItems";
import {useNavigate} from "react-router-dom";
import logo from './const/Frame.png';

const drawerWidth = 240;
export default function Navbar() {
    const navigate = useNavigate()
    return (
        <Drawer
            sx={navbarStyles.drawer}
            variant="permanent"
            anchor="left"
        >
            <img src={logo} className="avatar"
                 alt="avatr"
            />
            <Toolbar />
            <Divider />
            <List>
                {mainNavbarItems.map((item, index) => (
                    <ListItem
                        button
                        key={item.id}
                        onClick={() => navigate(item.route)}
                    >
                        <ListItemIcon
                            sx={navbarStyles.icons}
                        >
                            {item.icon}
                        </ListItemIcon>
                        <ListItemText
                            sx={navbarStyles.text}
                            primary={item.label}
                        />
                    </ListItem>
                ))}
            </List>
        </Drawer>
    );
};
