import {withRouter} from 'react-router-dom'
import React, {useEffect, useState} from "react";
import {APIService} from "../../Services/APIService";
import {Breadcrumb, Card, Col, Container, Form, InputGroup, Row} from "react-bootstrap";
import './blog.css'
import {Button} from "@material-ui/core";
import {useSelector} from "react-redux";
import {Formik} from 'formik'
import * as yup from "yup";
import axios from "axios";


class CreateBlogEditForm extends React.Component<{}> {

    constructor(props) {
        super(props);
        this.state = {
            blogAccesses: ['PUBLIC'],
            blogCategories: ['ALL'],
            formValues: ''
        }
    }

    componentDidMount() {
        APIService.getBlogCategories().then(response => {
            this.setState({blogCategories: response.data})
        })
        APIService.getBlogAccesses().then(response => {
            this.setState({blogAccesses: response.data})
        })
    }

    render() {
        const validationSchema = yup.object().shape({
                blogTitle: yup
                    .string().required('Blog title is required')
                    .min(3, 'Minimum 3 characters required')
                    .max(30, "Maximum 30 characters allowed"),
                data: yup
                    .string().required('Blog Data is required')
                    .min(3, "Minimum 3 characters are required")
                    .max(50000, "Max 50000 characters are allowed"),
                blogCategory: yup.string()
                    .required('Please, select category'),
                accessStatus: yup.string()
                    .required('Please, select access status'),
            }
        );

        const handleFormSubmission = (e) => {
            let dataBody = {
                blogId: this.props.blog.id,
                blogData: e.data,
            }
            let titleBody = {
                blogId: this.props.blog.id,
                blogTitle: e.blogTitle,
            }
            let accessBody = {
                blogId: this.props.blog.id,
                blogAccessStatus: e.accessStatus,
            }
            let categoryBody = {
                blogId: this.props.blog.id,
                blogCategory: e.blogCategory
            }

            if (this.props.blog.data !== e.data) {
                axios.put('http://localhost:8081/blog/update/data', dataBody, APIService.config).then(response => {
                    console.log(response.data)
                }).catch(err => {
                    console.warn("Error while updating blog data : " + err)
                    console.log('Error while updating blog data : Server Response', err.response);
                })
            }

            if (this.props.blog.blogAccessStatus !== e.accessStatus) {
                axios.put('http://localhost:8081/blog/update/access', accessBody, APIService.config).then(response => {
                    console.log(response.data)
                }).catch(err => {
                    console.warn("Error while updating blog access : " + err)
                    console.log('Error while updating blog access : Server Response', err.response);
                })
            }

            if (this.props.blog.blogTitle !== e.blogTitle) {
                axios.put('http://localhost:8081/blog/update/title', titleBody, APIService.config).then(response => {
                    console.log(response.data)
                }).catch(err => {
                    console.warn("Error while updating blog title : " + err)
                    console.log('Error while updating blog title : Server Response', err.response);
                })
            }

            if (this.props.blog.blogCategory !== e.blogCategory) {
                axios.put('http://localhost:8081/blog/update/category', categoryBody, APIService.config).then(response => {
                    console.log(response.data)
                }).catch(err => {
                    console.warn("Error while updating blog Category : " + err)
                    console.log('Error while updating blog Category : Server Response', err.response);
                })
            }

            return false;
        }

        return (
            <>
                <div className='createBlogForm'>
                    <Formik
                        initialValues={{
                            blogTitle: this.props.blog.blogTitle,
                            data: this.props.blog.data,
                            blogCategory: this.props.blog.blogCategory,
                            accessStatus: this.props.blog.blogAccessStatus
                        }}
                        onSubmit={(e) => handleFormSubmission(e)}
                        validationSchema={validationSchema}
                    >{({
                           handleSubmit,
                           handleChange,
                           handleBlur,
                           values,
                           touched,
                           isValid,
                           errors,
                       }) => (
                        <Form noValidate onSubmit={(e) => handleFormSubmission(e)}>
                            <Form.Group md="4" controlId="blogForm.BlogTitle">
                                <Form.Label>Blog Title</Form.Label>
                                <InputGroup hasValidation>
                                    <InputGroup.Text id="inputGroupPrepend"/>
                                    <Form.Control
                                        type="text"
                                        placeholder="Blog Title"
                                        aria-describedby="inputGroupPrepend"
                                        name="blogTitle"
                                        value={values.blogTitle}
                                        onChange={handleChange}
                                        isInvalid={!!errors.blogTitle}
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        {errors.blogTitle}
                                    </Form.Control.Feedback>
                                </InputGroup>
                            </Form.Group>

                            <Row className="mb-3">
                                <Form.Group as={Col} controlId="blogForm.FormGridAccess">
                                    <Form.Label>Select Access Type</Form.Label>
                                    <Form.Control
                                        as="select"
                                        type="select"
                                        name="accessStatus"
                                        value={values.accessStatus}
                                        onChange={handleChange}
                                        isInvalid={!!errors.accessStatus}
                                    >
                                        <option>Select Access Status</option>
                                        {
                                            this.state.blogAccesses.map((status, index) => {
                                                return (
                                                    <option key={index}>{status}</option>
                                                )
                                            })
                                        }
                                    </Form.Control>
                                    <Form.Control.Feedback type="invalid">
                                        {errors.accessStatus}
                                    </Form.Control.Feedback>
                                </Form.Group>

                                <Form.Group as={Col} controlId="BlogForm.FormGridCategory">
                                    <Form.Label>Select Category</Form.Label>
                                    <Form.Control
                                        as="select"
                                        type="select"
                                        name="blogCategory"
                                        value={values.blogCategory}
                                        onChange={handleChange}
                                        isInvalid={!!errors.blogCategory}
                                    >
                                        <option>Select Category</option>
                                        {
                                            this.state.blogCategories.map((category, index) => {
                                                return (
                                                    <option key={index}>{category}</option>
                                                )
                                            })
                                        }
                                    </Form.Control>
                                    <Form.Control.Feedback type="invalid">
                                        {errors.blogCategory}
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </Row>

                            <Form.Group placeholder='Type your blog here' className="mb-3"
                                        controlId="blogForm.DataTextArea">
                                <Form.Label>Blog Data</Form.Label>
                                <Form.Control
                                    as='textarea'
                                    type="textarea"
                                    placeholder="Type your blog here"
                                    name="data"
                                    value={values.data}
                                    onChange={handleChange}
                                    isInvalid={!!errors.data}
                                />
                                <Form.Control.Feedback type="invalid">
                                    {errors.data}
                                </Form.Control.Feedback>
                            </Form.Group>


                            <Button variant="primary" type="submit" onClick={(e) => handleSubmit(e)}>
                                Post
                            </Button>
                        </Form>
                    )}
                    </Formik>
                </div>
            </>
        );
    }
}

const EditButton = (props) => {
    const [showForm, setShowForm] = useState(false);

    return (
        <>
            <Button className='btn btn-primary' onClick={(e) => setShowForm(!showForm)}>
                Edit Blog
            </Button>
            {
                showForm ?
                    <CreateBlogEditForm blog={props.blog}/> :
                    <></>
            }
        </>
    )
}


function Blog(props) {
    const isUserAuthenticated = useSelector(state => state.getUser.authenticated)

    const user = useSelector(state => state.getUser.user)

    const [blog, setBlog] = useState([])

    const [dataMounted, setDataMounted] = useState(false)

    const [blogComments, setBlogComments] = useState([])

    const handleCommentSubmission = (e) => {
        const body = {
            blogId: blog.id,
            comment: e.comment,
            userName: user.userName
        }
        console.log(body)

        axios.post('http://localhost:8989/blog/comment', body, APIService.config).then(response => {
            console.log(response.data)
            window.location.reload()
        }).catch(err => {
            console.log("Error while fetching comment: " + err)
            console.log("Error while fetching comment Server response: " + err.response)
        })

        return false;
    }

    const commentSchema = yup.object().shape({
            comment: yup
                .string().required('Comment is required')
                .min(2, 'Minimum 2 characters required')
                .max(300, "Maximum 300 characters allowed"),
        }
    );

    useEffect(() => {

        APIService.getBlogById(props.match.params.id).then(response => {
            setBlog(response.data)

            let commentPromises = []

            if (!response.data.comments.includes(null)) {
                response.data.comments.forEach(commentId => {
                    let promise = APIService.getCommentById(commentId)
                    commentPromises.push(promise)
                })
            }

            Promise.all(commentPromises).then(comments => {
                setBlogComments(comments)
                console.log("Comments:", comments)
                console.log("blogComments:", blogComments)
            })

            setDataMounted(true)
        }, [])


    }, [])

    return (
        <>
            <div className='blog-container'>

                <Card className="bg-dark text-white blog-banner">
                    <Card.Img
                        className='blog-banner'
                        src="https://image.freepik.com/free-vector/abstract-dotted-banner-background_1035-18160.jpg"
                        alt="Card image"/>
                    <Card.ImgOverlay>
                        <Card.Title className='blog-title'>{blog.blogTitle}</Card.Title>
                    </Card.ImgOverlay>

                </Card>


                <Breadcrumb>
                    <Breadcrumb.Item href="#">Home</Breadcrumb.Item>
                    <Breadcrumb.Item href={blog.blogCategory}>
                        {blog.blogCategory}
                    </Breadcrumb.Item>
                    <Breadcrumb.Item active>{blog.blogTitle}</Breadcrumb.Item>
                </Breadcrumb>

                {dataMounted ?
                    <div className='blog-data-container'>
                        <p className='blog-data'>{blog.data}</p>
                        <p className='blog-owner'>Created By {blog.owner.userName}</p>
                    </div> : <></>
                }

                {isUserAuthenticated ?
                    <div>
                        <EditButton blog={blog}/>
                        <Button className='btn btn-primary'>
                            Delete Blog
                        </Button>
                    </div> : <></>
                }

                {
                    <div className='comment-form'>

                        {dataMounted && isUserAuthenticated && (blog.owner.userName === user.userName) ?
                            <Formik
                                initialValues={{
                                    blogTitle: '',
                                    data: '',
                                    blogCategory: '',
                                    accessStatus: ''
                                }}
                                onSubmit={(e) => handleCommentSubmission(e)}
                                validationSchema={commentSchema}
                            >{({
                                   handleSubmit,
                                   handleChange,
                                   handleBlur,
                                   values,
                                   touched,
                                   isValid,
                                   errors,
                               }) => (

                                <Form variant="dark">
                                    <Form.Group placeholder='Type your blog here'
                                                controlId="blogForm.CommentTextArea">
                                        <Form.Control
                                            as='textarea'
                                            type="textarea"
                                            placeholder="Type your comment here"
                                            name="comment"
                                            value={values.comment}
                                            onChange={handleChange}
                                            isInvalid={!!errors.comment}
                                        />
                                        <Form.Control.Feedback type="invalid">
                                            {errors.comment}
                                        </Form.Control.Feedback>
                                    </Form.Group>

                                    <Button type='submit'
                                            onClick={(e) => handleSubmit(e)}> Add Comment </Button>
                                </Form>
                            )}
                            </Formik>

                            : <></>
                        }
                    </div>}


                {dataMounted ?
                    <div className='comment-section-container'>
                        {
                            blogComments.map((comment, index) => {
                                return (
                                    <Container key={comment.data.commentId} className='comment-container' >
                                        <Row>
                                            <Col>Comment By {comment.data.commentOwner.userName}</Col>
                                        </Row>
                                        <Row>
                                            <Col>{comment.data.commentData}</Col>
                                        </Row>
                                        <Row>
                                            <Col>Added on { Date(comment.data.commentTimeStamp)}</Col>
                                        </Row>
                                    </Container>
                                )
                            })
                        }
                        <br/>
                    </div> : <></>
                }
            </div>
        </>
    )
}

export default withRouter(Blog);