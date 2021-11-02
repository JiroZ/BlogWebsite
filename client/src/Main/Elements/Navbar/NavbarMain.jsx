import {MenuItems} from './Components/MenuItems'
import UserModel from './Models/UserModel'
import './Navbar.css'
import {Navbar, Nav, Container, FormControl, Form, Button, NavDropdown} from 'react-bootstrap';
import axios from "axios";
import React from "react";
import {APIService} from "../../../Services/APIService";
import {HandleSearchRedirect} from "../../Pages/SearchRedirect";

class NavbarMain extends React.Component {
    constructor() {
        super();
        this.state = {
            selectedCategory: 'ALL',
            categories: [],
            searchString: '',
            redirect: null,
            searchData: null,
            createBlogRedirect: null
        }
    }

    componentDidMount() {
        APIService.getBlogCategories().then(response => {
            this.setState({categories: response.data})
            console.log(this.state.categories)
        })
    }

    render() {

        const config = {
            headers: {
                'Content-Type': 'application/JSON'
            }
        }

        const handleSearch = (e) => {
            e.preventDefault();
            this.setState({redirect: '/search'})
            const body = {searchString: this.state.searchString, category: this.state.selectedCategory}
            console.log(body)

            axios.post('http://localhost:8082/search', body, config).then(response => {
                this.setState({searchData: response.data})
            }).catch(err => {
                console.warn("Error while fetching searched data : " + err)
            })
        }

        return (
            <>
                <Navbar collapseOnSelect fixed='top' expand='sm' bg='dark' variant='dark'>
                    <Container>
                        <Navbar.Brand href="/">
                            <img
                                alt=""
                                src="https://upload.wikimedia.org/wikipedia/en/thumb/c/c8/Bloc_Quebecois_B_logo_1990s.svg/945px-Bloc_Quebecois_B_logo_1990s.svg.png"
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
                                    onChange={(e) => {
                                        this.setState({searchString: e.target.value})
                                    }}
                                    onSubmit={(e) => handleSearch(e)}
                                />
                                <Button variant="outline-success" type='submit'
                                        onClick={(e) => handleSearch(e)}> Search </Button>
                            </Form>

                            <NavDropdown
                                id="nav-dropdown-dark-example"
                                title={this.state.selectedCategory}
                                menuVariant="dark"
                            >
                                {
                                    this.state.categories.map((category, index) => {
                                        return (

                                            <NavDropdown.Item key={index}
                                                              onClick={() => this.setState({selectedCategory: category})}>{index}. {category}</NavDropdown.Item>
                                        )
                                    })
                                }
                            </NavDropdown>

                            <Nav>
                                {
                                    MenuItems.map((item, index) => {
                                        return (
                                            <Nav.Link key={index} href={item.url}>{item.title}</Nav.Link>
                                        )
                                    })
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

                {
                    (this.state.redirect != null && this.state.searchData != null) ?
                        <HandleSearchRedirect searchString={this.state.searchString}
                                              seachData={this.state.searchData}/> :
                        <></>
                }
            </>
        );
    }
}

export default NavbarMain;