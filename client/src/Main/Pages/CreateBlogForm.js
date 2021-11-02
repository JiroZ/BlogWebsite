import React from "react";
import {Button, Col, Form, InputGroup, Row} from "react-bootstrap";
import axios from "axios";
import {APIService} from "../../Services/APIService";
import {Formik} from 'formik'
import * as yup from 'yup';
import './CreateBlogForm.css'
import {withRouter} from "react-router-dom";

class CreateBlogForm extends React.Component {

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
            let body = {
                blogTitle: e.blogTitle,
                data: e.data,
                userName: "bhavishya",
                accessStatus: e.accessStatus,
                blogCategory: e.blogCategory
            }

            console.log(body)

            axios.post('http://localhost:8081/blog/create', body, APIService.config).then(response => {
                console.log(response.data)
            }).catch(err => {
                console.warn("Error while creating blog  : " + err)
                console.log('Error while creating blog : Server Response', err.response);
            })

            return false;
        }

        return (
            <>
                <div className='createBlogForm'>
                    <Formik
                        initialValues={{
                            blogTitle: '',
                            data: '',
                            blogCategory: '',
                            accessStatus: ''
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
        )
    }
}

export default withRouter(CreateBlogForm)