import {useState} from 'react';

import RegisterElements from './RegisterElements';
import {Button, FormControl} from "@material-ui/core";
import {Container} from "react-bootstrap";
import axios from "axios";

const RegisterForm = () => {
    const [userName, setUserName] = useState('')
    const [confirmUserName, setConfirmUserName] = useState('')
    const [email, setEmail] = useState('')
    const [confirmEmail, setConfirmEmail] = useState('')
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')

    const handleSubmit = (e) => {
        e.preventDefault();
        const user = {email, userName , password}

        let body = JSON.stringify(user)
        body.replace("'", "\"" )
        console.log(user)

        const config = {
            headers: {
                'Content-Type': 'application/JSON'
            }
        }

        axios.post(`http://localhost:8989/user/registration`, body, config).then(response => {
            console.log(response)
            console.log(response.data)
        }).catch((err) => {
            console.warn('error during http call', err.response);
        });
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
                        setUserName={setUserName}
                        setConfirmUserName={setConfirmUserName}
                    />
                    <Button variant="contained" type='submit' onClick={handleSubmit}> Register </Button>
                    <br/>
                </FormControl>
            </Container>
        </div>
    );
}
export default RegisterForm;