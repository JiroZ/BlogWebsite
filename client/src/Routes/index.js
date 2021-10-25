let port = 8080;
let host = 'localhost';

const authRoute = () =>  {
    return "http://"+host+":"+port+"/user/auth"
}

const registrationRoute = () => {
    return "http://"+host+":"+port+"/user/registration"
}

export {
    authRoute,
    registrationRoute
}