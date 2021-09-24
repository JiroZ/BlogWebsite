const userReducer = (state = {authenticated: false, user: {email: "", password: ""}}, action) => {
    switch (action.type) {
        case 'LOGGED_IN':
            return action.userData;
        default:
            return state;
    }
}
export default userReducer