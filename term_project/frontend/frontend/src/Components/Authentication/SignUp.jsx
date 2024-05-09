import React, {useState} from 'react';
import { TextField, Button, Container, Stack } from '@mui/material';
import { useNavigate, Link } from "react-router-dom"
import AuthServices from "../../Services/AuthServices";


const RegisterForm = () => {
    const navigate = useNavigate();
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [dateOfBirth, setDateOfBirth] = useState('');
    const [password, setPassword] = useState('');
    const [username, setUsername] = useState('');

    function handleSubmit(event) {

        event.preventDefault();
        AuthServices.signup(username,email,password,firstName,lastName,dateOfBirth).then(res=>{
            console.log(firstName, lastName, username, email, dateOfBirth, password)
            navigate("/login")
        },
            (error) => {
                const resMessage =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
                alert(resMessage);
            })


    }

    return (
        <React.Fragment>
            <h2 className="login signup-header">Register Form</h2>
            <form onSubmit={handleSubmit} action={<Link to="/login"/>} className="login signup-header">
                <Stack spacing={2} direction="row" sx={{marginBottom: 4}}>
                    <TextField
                        type="text"
                        variant='outlined'
                        color='secondary'
                        label="First Name"
                        onChange={e => setFirstName(e.target.value)}
                        value={firstName}
                        fullWidth
                        required
                    />
                    <TextField
                        type="text"
                        variant='outlined'
                        color='secondary'
                        label="Last Name"
                        onChange={e => setLastName(e.target.value)}
                        value={lastName}
                        fullWidth
                        required
                    />
                </Stack>
                <TextField
                    type="text"
                    variant='outlined'
                    color='secondary'
                    label="Username"
                    onChange={e => setUsername(e.target.value)}
                    value={username}
                    required
                    fullWidth
                    sx={{mb: 4}}
                />
                <TextField
                    type="email"
                    variant='outlined'
                    color='secondary'
                    label="Email"
                    onChange={e => setEmail(e.target.value)}
                    value={email}
                    fullWidth
                    required
                    sx={{mb: 4}}
                />
                <TextField
                    type="password"
                    variant='outlined'
                    color='secondary'
                    label="Password"
                    onChange={e => setPassword(e.target.value)}
                    value={password}
                    required
                    fullWidth
                    sx={{mb: 4}}
                />
                <TextField
                    type="date"
                    variant='outlined'
                    color='secondary'
                    label="Date of Birth"
                    onChange={e => setDateOfBirth(e.target.value)}
                    value={dateOfBirth}
                    fullWidth
                    required
                    sx={{mb: 4}}
                />
                <Button variant="outlined" color="secondary" type="submit" >Register</Button>
            </form>
            <small className="small-text">Already have an account? <Link to="/login">Login Here</Link></small>

        </React.Fragment>
    )
}

export default RegisterForm;