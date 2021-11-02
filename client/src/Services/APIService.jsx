import axios from "axios";

let host = 'localhost'
let port = '8989'

class APIService {

    static config = {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
        }
    }

    static registerUser(body) {
        return axios.post(`http://${host}:8083/user/registration`, body)
    }

    static authUser(body) {
        return axios.post(`http://${host}:8083/user/auth`, body)
    }

    static getAccessibleData() {
        return axios.get(`http://${host}:8082`)
    }

    static getBlogCategories() {
        return axios.get(`http://${host}:${port}/categories`)
    }

    static getSearchedData(body) {
        return axios.post(`http://${host}:${port}/search`, body, APIService.config)
    }

    static getBlogAccesses() {
        return axios.get(`http://${host}:${port}/blog/accesses`, APIService.config)
    }

    static getBlogById(id) {
        return axios.get(`http://${host}:${port}/blog/` + id, APIService.config)
    }

    static getCommentById(commentId) {
        return axios.get(`http://${host}:${port}/blog/comment/` + commentId, APIService.config)
    }
}

export {
    APIService
}