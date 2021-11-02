import {Button} from "react-bootstrap";
import {Redirect} from "react-router-dom";
import React, {useState} from "react";
import CreateBlogForm from "../CreateBlogForm";
import {useSelector} from "react-redux";

const BackButton = () => {
    const [redirect, setRedirect] = useState('')

    return (
        <div>
            <Button onClick={() => setRedirect('redirect')}>
                Go Back ->
            </Button>
            {
                (redirect === 'redirect') ?
                    <RedirectHome/> :
                    <></>
            }
        </div>

    )
}

const RedirectHome = () => {
    return (
        <Redirect to='/'/>
    )
}

const BlogCreateButton = () => {
    const isUserAuthenticated = useSelector(state => state.getUser.authenticated)

    const RedirectBlogCreate = () => {
        return (
            <>
                <Redirect to='/blog/create'/>
                <CreateBlogForm/>
            </>
        )
    }

    const [redirect, setRedirect] = useState('')

    return (
        <>{
            isUserAuthenticated ?
                <div>
                    <label>
                        Add a new blog -
                    </label>
                    <Button onClick={(e) => setRedirect('redirect')}>
                        Create
                    </Button>

                    {
                        (redirect === 'redirect') ?
                            <RedirectBlogCreate/> :
                            <></>
                    }
                </div>
                : <p> Login to create blog</p>
        }
        </>
    )
}
export {
    BackButton,
    RedirectHome,
    BlogCreateButton
}