import axios from "axios";

let host = 'localhost'
let port = '8989'

const config = {
    headers: {
        'Content-Type': 'application/JSON',
        'Access-Control-Allow-Origin': '*'
    },
    mode: 'no-cors',
    credentials: 'same-origin'
}

class APIService {
    static registerUser(props) {
        console.log("Register User")
        return axios.post(`http://${host}:${port}/user/registration`, props.body, config)
    }

    static authUser(props) {
        console.log("Auth User")
        return axios.post(`http://${host}:${port}/user/auth`, props.body, config)
    }

    static getAccessibleData() {
        console.log("Accessible Data")
        return axios.get(`http://${host}:${port}`, config)
    }

     static async getSearchedData(body) {
        console.log("Searched Data")
        return axios.post(`http://${host}:${port}/search`, body, config)
    }
}

export default APIService