import {MenuItems} from './MenuItems'
import {useState} from "react";
import UserModel from './Models/UserModel'

import './Navbar.css'


const Navbar = (props) => {
    let [clicked] = useState(false)

    const handleClick = () => {
        clicked = !clicked
    }

    return (
        <>
            <nav className="NavbarItems">
                <h1 className="navbar-logo">React<i className='fab fa-react'/></h1>

                <div className="menu-icon" onClick={handleClick}>
                    <i className={clicked ? 'fas fa-times' : 'fas fa-bars'}/>
                </div>

                <ul className={clicked ? 'nav-menu active' : 'nav-menu'}> {
                    MenuItems.map((item, index) => {
                        return (
                            <li key={index}>
                                <a className={item.cName} href={item.url}>
                                    {item.title}
                                </a>
                            </li>
                        )
                    })}

                    <UserModel
                        signedIn={props.signedIn}
                    />
                </ul>
            </nav>
        </>
    );
}
export default Navbar;