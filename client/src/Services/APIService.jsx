import axios from "axios";

let host = 'localhost'
let port = '8989'

const config = {
    headers : {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin' :'*'
    }
}

class APIService {
    static registerUser(props) {
        console.log("Register User")
        return axios.post(`http://${host}:${port}/user/registration`, props.body)
    }

    static authUser(props) {
        console.log("Auth User")
        return axios.post(`http://${host}:${port}/user/auth`, props.body)
    }

    static getAccessibleData() {
        console.log("Accessible Data")
        return axios.get(`http://${host}:${port}`)
    }

     static getSearchedData(body) {
        console.log("Searched Data")
        return axios.post(`http://${host}:${port}/search`, body, config)
    }
}
export default APIService