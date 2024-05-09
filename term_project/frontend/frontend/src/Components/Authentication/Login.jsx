import React, {useState} from "react";
import { TextField, Button } from "@mui/material";
import { useNavigate, Link } from "react-router-dom"
import AuthServices from "../../Services/AuthServices";
const Login = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [usernameError, setUsernameError] = useState(false)
    const [passwordError, setPasswordError] = useState(false)

    function login() {

            AuthServices.login(username, password).then(() => {
                    console.log("logged in");
                    navigate(`/access/dashboard/groups/${username}`);
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



    const handleSubmit = (event) => {
        event.preventDefault()

        setUsernameError(false)
        setPasswordError(false)

        if (username == '') {
            setUsernameError(true)
        }
        if (password == '') {
            setPasswordError(true)
        }

        if (username && password) {
            console.log(username, password)
            login();

        }

    }

    return (
        <React.Fragment>
            <form autoComplete="off" onSubmit={handleSubmit} className="login">
                <h2>Login Form</h2>
                <TextField
                    label="Username"
                    onChange={e => setUsername(e.target.value)}
                    required
                    variant="outlined"
                    color="secondary"
                    type="text"
                    sx={{mb: 3}}
                    fullWidth
                    value={username}
                    error={usernameError}
                />
                <TextField
                    label="Password"
                    onChange={e => setPassword(e.target.value)}
                    required
                    variant="outlined"
                    color="secondary"
                    type="password"
                    value={password}
                    error={passwordError}
                    fullWidth
                    sx={{mb: 3}}
                />
                <Button variant="outlined" color="secondary" type="submit">Login</Button>

            </form>
            <small className="small-text">Need an account? <Link to="/signup">Register here</Link></small>
        </React.Fragment>
    );
}

export default Login;