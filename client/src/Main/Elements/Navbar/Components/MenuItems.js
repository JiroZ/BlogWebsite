import HomeComponent from './HomeComponent.jsx'
import ContactUsComponent from './ContactUsComponent.jsx'

export const MenuItems = [
    {
        title:'Home',
        url:'/',
        cName:'nav-links',
        component: HomeComponent
    },
    {
        title:'Contact Us',
        url:'/contact-us',
        cName:'nav-links-auth',
        component: ContactUsComponent
    },
]