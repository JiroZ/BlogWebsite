import {useState} from 'react';

import LoginElements from './LoginElements';
import {Button, FormControl} from "@material-ui/core";

import axios from 'axios'

import  {useDispatch, useSelector} from "react-redux";
import {CloseUserModel, InitUser, SignIn} from "../../../../Redux/UserLogin/Actions";


const LoginForm = () => {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const dispatch = useDispatch();

    const handleSubmit = (e) => {
        e.preventDefault();
        const user = {email, password}

        let body = JSON.stringify(user)

        const config = {
            headers: {
                'Content-Type': 'application/JSON'
            }
        };

        console.log(user)

        axios.post('http://localhost:8080/users/auth', body, config).then(response => {
            console.log(response)
            if(response.data.authenticated) {
                dispatch(InitUser(response.data))
                dispatch(SignIn())
                dispatch(CloseUserModel())
            }
        })
    };

    return (
        <div className="form">
            <FormControl noValidate autoComplete="off" onSubmit={handleSubmit}>
                <LoginElements
                    email={email}
                    password={password}
                    setEmail={setEmail}
                    setPassword={setPassword}
                />
                <Button variant="contained" type='submit' onClick={handleSubmit}> Login </Button>
                <br/>
            </FormControl>
        </div>
    );
}
export default LoginForm;