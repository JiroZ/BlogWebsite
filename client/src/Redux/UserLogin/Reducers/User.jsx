const userReducer = (state = {authenticated: false, user: {email: "", password: ""}}, action) => {
    switch (action.type) {
        case 'LOGGED_IN':
            return action.userAuthResponse;
        default:
            return state;
    }
}
export default userReducer