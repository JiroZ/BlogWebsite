import {TextField} from "@material-ui/core";
import {useState} from "react";

const LoginElements = (props) => {
    const [userNameError] = useState(false);

    return (
        <>
            <TextField fullWidth id="fullWidth" error={userNameError} label="User Name" type="userName" required value={props.userName}
                       onChange={(e) => props.setUserName(e.target.value)}/>
            <br/>

            <TextField fullWidth id="fullWidth" label="Password" type="password" required value={props.password}
                       onChange={(e) => props.setPassword(e.target.value)}/>
            <br/>
        </>
    )
}

export default LoginElements;