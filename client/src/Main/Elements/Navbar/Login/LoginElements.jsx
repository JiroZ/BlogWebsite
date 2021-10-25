import {Button, TextField} from "@material-ui/core";
import {useState} from "react";

const LoginElements = (props) => {
    const [emailError, setEmailError] = useState(false);
    const [userNameError, setUserNameError] = useState(false);

    function handleEmailFieldChange(e) {
        props.setEmail(e.target.value)

        if (!(e.target.value.match("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\\])"))) {
            setEmailError(true)
        } else {
            setEmailError(false)
        }
    }

    function handleUserNameFieldChange(e) {
        props.setUserName(e.target.value)
        if(!(e.target.value.match("^[A-Za-z0-9]{1,10}$"))) {
            setUserNameError(true)
        } else {
            setUserNameError(false)
        }
    }



    function handleUserNameChange(e) {
        props.setEmail(e.target.value)
    }

    return (
        <>
            <TextField error={userNameError} label="User Name" type="userName" required value={props.userName}
                       onChange={(e) => props.setUserName(e.target.value)}/>
            <br/>

            <TextField label="Password" type="password" required value={props.password}
                       onChange={(e) => props.setPassword(e.target.value)}/>
            <br/>

            <label>{props.userName}</label>
            <label>{props.email}</label>
            <label>{props.password}</label>
        </>
    )
}

export default LoginElements;