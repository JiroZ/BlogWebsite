import {MenuItems} from './Components/MenuItems'
import UserModel from './Models/UserModel'
import './Navbar.css'
import {Navbar, Nav, Container, FormControl, Form, Button} from 'react-bootstrap';
import axios from "axios";
import React from "react";

const handleSearch = (e) => {
    const category = 'ALL'
    const searchString = e.target.value
    const search = [searchString, category]

    const body = JSON.stringify(search)

    const config = {
        headers: {
            'Content-Type': 'application/JSON'
        }
    }

    axios.post('http://localhost:8989/search', body, config).then(response => {
        console.log("Searched data" + response)
    }).catch(err => {
        console.warn("Error while fetching searched data : " + err)
    })
}

const NavbarMain = () => {


    return (
        <>
            <Navbar collapseOnSelect fixed='top' expand='sm' bg='dark' variant='dark'>
                <Container>
                    <Navbar.Brand href="/">
                        <img
                            alt=""
                            src="/resources/logo.svg"
                            width="30"
                            height="30"
                            className="d-inline-block align-top"
                        />{' '}
                        Bloggie
                    </Navbar.Brand>

                    <Navbar.Toggle aria-controls='responsive-navbar-nav'/>
                    <Navbar.Collapse id='responsive-navbar-nav'>

                        <Form className="d-flex">
                            <FormControl
                                type="search"
                                placeholder="Search"
                                className="me-2"
                                aria-label="Search"
                                onSubmit={(e) => handleSearch(e)}
                            />
                            <Button variant="outline-success" type='submit'
                                    onClick={(e) => handleSearch(e)}> Search </Button>
                        </Form>
                        <Nav>
                            {

                                        MenuItems.map((item, index) => {
                                                return (

                                                    <li key={index}>
                                                        <Nav.Link href={item.url}>{item.title}</Nav.Link>
                                                    </li>
                                                )
                                            }
                                        )

                            }
                        </Nav>
                    </Navbar.Collapse>
                    <Navbar.Toggle/>
                </Container>

                <Navbar.Text className="justify-content-end">
                    <UserModel/>
                </Navbar.Text>
            </Navbar>
            <div
                style={{
                    display: 'flex',
                    justifyContent: 'Center',
                    alignItems: 'Right',
                    height: '10vh'
                }}
            />
        </>
    );
}
export default NavbarMain;