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

const InitUser = (user) => {
    return {
        type: 'LOGGED_IN',
        userData: user
    }
}

export {
    OpenUserModel,
    CloseUserModel,
    SignIn,
    InitUser
}