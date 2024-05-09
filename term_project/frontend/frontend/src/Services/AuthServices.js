import axios from 'axios';
import api from '../baseUrl';

function login(username, password){
    return (api.post("/auth/signin",{
        "username":username,
        "password":password
    }).then((response) => {
        console.log(response);
        if (response.data.accessToken) {
            localStorage.setItem("user", JSON.stringify(response.data));
        }

        return response.data;
    })
    )
}

const signup = (username, email,password, firstName, lastName, dob) =>{
    return (api.post("auth/signup", {
        "username":username,
        "email": email,
        "password":password,
        "firstName": firstName,
        "lastName" : lastName,
        "dob": dob
    }));
};

const AuthServices = {
    login,
    signup
};

export default AuthServices