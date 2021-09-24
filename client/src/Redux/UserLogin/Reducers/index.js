import loggedReducer from "./isSignedIn";
import {combineReducers} from "redux";
import modelReducer from "./isModelOpen";
import userReducer from "./User";



const allReducers = combineReducers({
    isSignedIn: loggedReducer,
    isUserModelOpen: modelReducer,
    getUser: userReducer
})

export default allReducers