import {TextField} from "@material-ui/core";
import {useState} from "react";

const RegisterElements = (props) => {
    const [userNameError, setUserNameError] = useState(false);
    const [userNameConfirmError, setUserNameConfirmError] = useState(false);
    const [error, setError] = useState(false)
    const [confirmEmailError, setConfirmEmailError] = useState(false);
    const [passwordError] = useState(false);
    const [passwordConfirmError, setPasswordConfirmError] = useState(false);

    function handleEmailChange(e) {
        props.setEmail(e.target.value)

        if (!(e.target.value.match("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\\])"))) {
            setError(true)
        } else {
            setError(false)
        }
    }

    function handleUserNameFieldChange(e) {
        props.setUserName(e.target.value)
        if (!(e.target.value.match("^[A-Za-z0-9]{3,14}$"))) {
            setUserNameError(true)
        } else {
            setUserNameError(false)
        }
    }

    function handleUserNameConfirmFieldChange(e) {
        props.setConfirmUserName(e.target.value)

        const matches = props.userName === props.confirmUserName

        console.log(matches)
        console.log(props.email, props.confirmEmail)

        if (!(matches)) {
            setUserNameConfirmError(true)
        } else {
            setUserNameConfirmError(false)
        }
    }

    function handleConfirmEmailChange(e) {
        props.setConfirmEmail(e.target.value)

        const matches = props.email === props.confirmEmail

        console.log(matches)
        console.log(props.email, props.confirmEmail)

        if (!(matches)) {
            setConfirmEmailError(true)
        } else {
            setConfirmEmailError(false)
        }
    }

    function handlePasswordChange(e) {
        props.setPassword(e.target.value)
    }

    function handleConfirmPasswordChange(e) {
        props.setConfirmPassword(e.target.value)

        const matches = props.password === props.confirmPassword

        console.log(matches)
        console.log(props.password, props.confirmPassword)

        if (!(matches)) {
            setPasswordConfirmError(true)
        } else {
            setPasswordConfirmError(false)
        }
    }

    return (
        <>
            <TextField error={userNameError} label="User Name" type="userName"
                       onChange={(e) => handleUserNameFieldChange(e)} required
                       value={props.userName}/>

            <TextField error={userNameConfirmError} label="Confirm User Name" type="userName" required
                       value={props.confirmUserName}
                       onChange={(e) => handleUserNameConfirmFieldChange(e)}/>
            <br/>
            <TextField error={error} label="Email" type="email" onChange={(e) => handleEmailChange(e)}
                       required
                       value={props.email}/>

            <TextField error={confirmEmailError} label="Confirm Email" type="email" required value={props.confirmEmail}
                       onChange={(e) => handleConfirmEmailChange(e)}/>
            <br/>
            <TextField error={passwordError} label="Password"
                       type="password"
                       required value={props.password}
                       onChange={(e) => handlePasswordChange(e)}/>
            <TextField error={passwordConfirmError} label="Confirm Password" type='password' required
                       value={props.confirmPassword}
                       onChange={(e) => handleConfirmPasswordChange(e)}/>
            <br/>
        </>
    )
}

export default RegisterElements;