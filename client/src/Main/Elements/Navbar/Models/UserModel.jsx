import {useState} from "react";

import RegisterBody from '../Register/RegisterForm'
import LoginBody from '../Login/LoginForm'

import {useDispatch, useSelector} from "react-redux";

import {CloseUserModel, OpenUserModel} from "../../../../Redux/UserLogin/Actions";

import {Modal, Navbar, Nav, Container, Button} from 'react-bootstrap';

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
            setLoginModel(false);
        }
    }

    function handleSignIn() {
        if (isLoginModel) {
            console.log("Login");
            //Todo: send Sign in request
        } else {
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
            {
                isUserAuthenticated ?
                    <div><label>Welcome {userEmail}</label><Button className="userActionButton"> + </Button></div> :

                    <Nav.Link className="userActionButton" onClick={handleUserModel}>
                        Sign In
                    </Nav.Link>
            }

            {/*<Modal*/}
            {/*    open={isModelOpen}*/}
            {/*    disablePortal*/}
            {/*    disableEnforceFocus*/}
            {/*    disableAutoFocus*/}
            {/*    aria-labelledby="simple-modal-title"*/}
            {/*    aria-describedby="simple-modal-description"*/}
            {/*>*/}
            <Modal
                show={isModelOpen}
                onHide={() => handleCloseModel}
                dialogClassName="modal-90w"
                aria-labelledby="example-custom-modal-styling-title"
            >
                <Modal.Header>
                    <Modal.Title id="example-custom-modal-styling-title">
                        <label className='titleName'>{isLoginModel ? "Login" : "Register"}</label>
                        <Button onClick={handleCloseModel}><i className="fas fa-times"/>X</Button>
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                        <div className='taskBar'>
                        </div>
                        <div className='modelBody'>
                            {isLoginModel ? <LoginBody/> : <RegisterBody/>}
                        </div>
                        {isLoginModel ?
                            <label>Don't have a account?</label>:
                            <label>Have a account?</label>
                        }
                        {isLoginModel ?
                            <Button variant="contained" onClick={handleRegister}>Register</Button> :
                            <Button variant="contained" onClick={handleSignIn}>Sign In</Button>
                        }
                </Modal.Body>
            </Modal>
        </>
    );
}

export default UserModel