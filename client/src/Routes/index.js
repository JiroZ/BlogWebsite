let port = 8080;
let host = 'locahost';

const authRoute = () =>  {
    return "http://"+host+":"+port+"/users/auth"
}

const registrationRoute = () => {
    return "http://"+host+":"+port+"/users/registration"
}

export {
    authRoute,
    registrationRoute
}