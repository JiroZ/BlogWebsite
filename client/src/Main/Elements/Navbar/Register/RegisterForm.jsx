import {useState} from 'react';

import RegisterElements from './RegisterElements';
import {Button, FormControl} from "@material-ui/core";
import axios from "axios";
import {CloseUserModel, InitUser} from "../../../../Redux/UserLogin/Actions";
import  {useDispatch, useSelector} from "react-redux";

const LoginForm = () => {
    const [email, setEmail] = useState('')
    const [confirmEmail, setConfirmEmail] = useState('')
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')
    const dispatch = useDispatch();

    const handleSubmit = (e) => {
        e.preventDefault();
        const user = {email, password}

        let body= JSON.stringify(user)

        const config = {
            headers: {
                'Content-Type': 'application/JSON'
            }
        };

        console.log(user)

        axios.post('http://localhost:8080/users/registration', body, config).then(response => {
            console.log(response)
            if(response.data.registered) {
                dispatch(InitUser(response.data.user.userSignInDetails))
                dispatch(CloseUserModel())
            }
        })

        // fetch('http://localhost:8080/user/register', {
        //     method: 'POST',
        //     body: data,
        // }).then(r => {
        //     console.log(r.body)
        // });
    };

    return (
        <div className="form">
            <FormControl noValidate autoComplete="off" onSubmit={handleSubmit}>
                <RegisterElements
                    email={email}
                    confirmEmail={confirmEmail}
                    password={password}
                    confirmPassword={confirmPassword}
                    setEmail={setEmail}
                    setConfirmEmail={setConfirmEmail}
                    setPassword={setPassword}
                    setConfirmPassword={setConfirmPassword}
                />
                <Button variant="contained"  type='submit' onClick={handleSubmit}> Submit </Button>
                <br/>
            </FormControl>
        </div>
    );
}
export default LoginForm;