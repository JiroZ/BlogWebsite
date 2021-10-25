import {MenuItems} from './Components/MenuItems'
import UserModel from './Models/UserModel'
import './Navbar.css'
import {Navbar, Nav, Container, FormControl, Form, Button} from 'react-bootstrap';

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
                            />
                            <Button variant="outline-contained">Search</Button>
                        </Form>

                        <Nav>
                            {
                                MenuItems.map((item, index) => {
                                    return (
                                        <Nav.Link href={item.url}>{item.title}</Nav.Link>
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
        </>
    );
}
export default NavbarMain;