const OpenUserModel = () => {
    return {
        type: 'OPEN'
    }
}
const CloseUserModel = () => {
    return {
        type: 'CLOSE'
    }
}
const SignIn = () => {
    return {
        type: 'SIGN_IN'
    }
}

const InitAuthResponse = (authResponse) => {
    return {
        type: 'LOGGED_IN',
        userAuthResponse: authResponse
    }
}



export {
    OpenUserModel,
    CloseUserModel,
    SignIn,
    InitAuthResponse
}