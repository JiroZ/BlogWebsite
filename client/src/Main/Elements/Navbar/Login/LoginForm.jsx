import {useState} from 'react';

import LoginElements from './LoginElements';
import {Button, FormControl} from "@material-ui/core";

import axios from 'axios'

import  {useDispatch} from "react-redux";
import {CloseUserModel, InitAuthResponse, SignIn} from "../../../../Redux/UserLogin/Actions";

import {LoadAuthToken, SaveAuthResponse, SaveAuthToken} from "../../../ReduxUtils/ReduxPersist.jsx"

import './LoginForm.css'

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

        axios.post(`http://localhost:8083/user/auth`, body, config).then(response => {
            console.log(response)
            if(response.data.authenticated) {
                dispatch(InitAuthResponse(response.data))
                dispatch(SignIn())
                dispatch(CloseUserModel())
                SaveAuthToken(response.data.token)
                SaveAuthResponse(response.data)
            }
        }).catch((err) => {
            console.warn('error during http call', err);
            console.warn('error during http call Response: ', err.response);
        });
        console.log(LoadAuthToken())
    };

    const formDisabledCondition = () => {
        return (password === '' && userName === '')
    }

    return (
        <div className="form login-form">
            <FormControl noValidate autoComplete="off" onSubmit={handleSubmit}>
                <LoginElements
                    password={password}
                    setPassword={setPassword}
                    userName={userName}
                    setUserName={setUserName}
                />
                <Button disabled={formDisabledCondition()} variant="contained" type='submit' onClick={handleSubmit}> Login </Button>
                <br/>
            </FormControl>
        </div>
    );
}
export default LoginForm;