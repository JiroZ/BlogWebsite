import {useState} from 'react';

import RegisterElements from './RegisterElements';
import {Button, FormControl} from "@material-ui/core";
import axios from "axios";
import {CloseUserModel, InitUser} from "../../../../Redux/UserLogin/Actions";
import {useDispatch, useSelector} from "react-redux";
import {Container} from "react-bootstrap";

const RegisterForm = () => {
    const [userName, setUserName] = useState('')
    const [confirmUserName, setConfirmUserName] = useState('')
    const [email, setEmail] = useState('')
    const [confirmEmail, setConfirmEmail] = useState('')
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')
    const dispatch = useDispatch();

    const handleSubmit = (e) => {
        e.preventDefault();
        const user = {email, userName , password}

        let body = JSON.stringify(user)

        const config = {
            headers: {
                'Content-Type': 'application/JSON'
            }
        };

        console.log(user)

        axios.post('http://localhost:8080/user/registration', body, config).then(response => {
            console.log(response)
            if (response.data.registered) {
                dispatch(InitUser(response.data.user.userSignInDetails))
                dispatch(CloseUserModel())
            }
        })


    };

    return (
        <div>
            <Container className="form">
                <FormControl noValidate autoComplete="off" onSubmit={handleSubmit}>
                    <RegisterElements
                        userName={userName}
                        confirmUserName={confirmUserName}
                        email={email}
                        confirmEmail={confirmEmail}
                        password={password}
                        confirmPassword={confirmPassword}
                        setEmail={setEmail}
                        setConfirmEmail={setConfirmEmail}
                        setPassword={setPassword}
                        setConfirmPassword={setConfirmPassword}
                    />
                    <Button variant="contained" type='submit' onClick={handleSubmit}> Register </Button>
                    <br/>
                </FormControl>
            </Container>
        </div>
    );
}
export default RegisterForm;