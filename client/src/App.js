import './App.css';
import NavbarRouter from "./Main/Elements/Navbar/NavbarRouter.jsx"

import TestComponent from "./TestComponent";

import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';

function App() {
  return (
    <div className="App">
        <NavbarRouter/>
        <TestComponent/>
    </div>
  );
}
export default App;
