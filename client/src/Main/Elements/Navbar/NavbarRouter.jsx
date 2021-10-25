import NavbarMain from './NavbarMain.jsx'
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Home from "./Components/Home";

import {MenuItems} from "./Components/MenuItems";

const NavbarRouter = () => {
    return (
        <>
            <Router>
                <NavbarMain/>
                <Switch>
                    {
                        MenuItems.map((item, index) => {
                            return (
                                <Route path={item.url} exact component={Home}/>
                            )
                        })
                    }
                    {/*<Route path='/' exact component={Home}/>*/}
                </Switch>
            </Router>
        </>
    )
}
export default NavbarRouter