import {Button, Modal} from "@material-ui/core";
import {useState} from "react";

import RegisterBody from '../Register/RegisterForm'
import LoginBody from '../Login/LoginForm'

import {useDispatch, useSelector} from "react-redux";

import './UserModel.css'
import {CloseUserModel, OpenUserModel} from "../../../../Redux/UserLogin/Actions";

const UserModel = () => {
    const [isLoginModel, setLoginModel] = useState(true);

    const isModelOpen = useSelector(state => state.isUserModelOpen)

    const isUserAuthenticated = useSelector(state => state.getUser.authenticated)
    const userEmail = useSelector(state => state.getUser.user.email)

    const dispatch = useDispatch();

    function handleRegister() {
        if (!isLoginModel) {
            console.log("Register");
            //Todo: Send Register Request
        } else {
            // MainModelBody.setState(RegisterBody);

            setLoginModel(false);
        }
    }

    function handleSignIn() {
        if (isLoginModel) {
            console.log("Login");
            //Todo: send Sign in request
        } else {
            // MainModelBody.setState(LoginBody);
            setLoginModel(true);
        }
    }

    function handleUserModel() {
        dispatch(OpenUserModel())
    }

    function handleCloseModel() {
        dispatch(CloseUserModel())
    }

    return (
        <>
            {isUserAuthenticated ?
                <div><label>Welcome {userEmail}</label><Button className="userActionButton"> + </Button></div> :
                <Button className="userActionButton" onClick={handleUserModel}>
                    Sign In
                </Button>}

            <Modal
                open={isModelOpen}
                disablePortal
                disableEnforceFocus
                disableAutoFocus
                aria-labelledby="simple-modal-title"
                aria-describedby="simple-modal-description"
            >

                <div className="userModel">
                    <div className='taskBar'>
                        <label className='titleName'>{isLoginModel ? "Login" : "Register"}</label>
                        <Button onClick={handleCloseModel}><i className="fas fa-times"/></Button>
                    </div>
                    <div className='modelBody'>
                        {isLoginModel ? <LoginBody/> : <RegisterBody/>}
                    </div>
                    <Button variant="contained" onClick={handleRegister}>Register</Button>
                    <Button variant="contained" onClick={handleSignIn}>Sign In</Button>
                </div>
            </Modal>
        </>
    );
}

export default UserModel