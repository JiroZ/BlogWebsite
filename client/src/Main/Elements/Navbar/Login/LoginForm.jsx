import {useState} from 'react';

import LoginElements from './LoginElements';
import {Button, FormControl} from "@material-ui/core";

import axios from 'axios'

import  {useDispatch} from "react-redux";
import {CloseUserModel, InitAuthResponse, SignIn} from "../../../../Redux/UserLogin/Actions";


const LoginForm = () => {
    const [password, setPassword] = useState('')
    const [userName, setUserName] = useState('');
    const dispatch = useDispatch();

    const handleSubmit = (e) => {
        e.preventDefault();
        const user = {userName, password}

        let body = JSON.stringify(user)
        console.log(user)

        const config = {
            headers: {
                'Content-Type': 'application/JSON'
            }
        }

        axios.post(`http://localhost:8989/user/auth`, body, config).then(response => {
            console.log(response)
            if(response.data.authenticated) {
                dispatch(InitAuthResponse(response.data))
                dispatch(SignIn())
                dispatch(CloseUserModel())
            }
        }).catch((err) => {
            console.warn('error during http call', err);
            console.warn('error during http call Response: ', err.response);
        });
    };

    return (
        <div className="form">
            <FormControl noValidate autoComplete="off" onSubmit={handleSubmit}>
                <LoginElements
                    password={password}
                    setPassword={setPassword}
                    userName={userName}
                    setUserName={setUserName}
                />
                <Button variant="contained" type='submit' onClick={handleSubmit}> Login </Button>
                <br/>
            </FormControl>
        </div>
    );
}
export default LoginForm;