import NavbarMain from './NavbarMain.jsx'
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import {MenuItems} from "./Components/MenuItems";
import CreateBlogForm from "../../Pages/CreateBlogForm";
import Blog from "../../blog";

const NavbarRouter = () => {
    return (
        <>
            <Router>
                <NavbarMain/>
                <Switch>
                    {
                        MenuItems.map((item, index) => {
                            return (
                                <Route key={index} path={item.url} exact component={item.component}/>
                            )
                        })
                    }

                    <Route path='/blog/create' component={CreateBlogForm}/>
                    <Route path="/blog/:id" component={Blog}><Blog/></Route>
                </Switch>
            </Router>
        </>
    )
}
export default NavbarRouter