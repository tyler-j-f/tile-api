import './css/App.css';
import TileNftHeader from "./header/TileNftHeader";
import { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
// components
import Toggle from './components/Toggle';
import Menu from './components/Menu';
// pages
import HomePage from './pages/HomePage';
import ExamplePage from './pages/ExamplePage';
import ContactPage from './pages/ContactPage'

// function TileNftApp() {
//   return (
//     <div className="App">
//       <TileNftHeader />
//     </div>
//   );
// }
//
// export default TileNftApp;

function TileNftApp() {

  const [navToggled, setNavToggled] = useState(false);

  const handleNavToggle = () => {
    setNavToggled(!navToggled);
  }

  // return (
  //     <div className="App">
  //       <Toggle handleNavToggle={handleNavToggle}/>
  //       <Router>
  //         { navToggled ? <Menu handleNavToggle={handleNavToggle} /> : null }
  //         <Switch>
  //           <Route exact path="/" component={HomePage} />
  //           <Route exact path="/example" component={ExamplePage} />
  //           <Route exact path="/contact" component={ContactPage} />
  //         </Switch>
  //       </Router>
  //     </div>
  // );

  return (
      <div className="App">
        <Toggle handleNavToggle={handleNavToggle}/>
        <Router>
          { navToggled ? <Menu handleNavToggle={handleNavToggle} /> : null }
          <Routes>
            <Route exact path="/" element={<HomePage/>}/>
            <Route exact path="/example" element={<ExamplePage/>}/>
            <Route exact path="/contact" element={<ContactPage/>}/>
          </Routes>
        </Router>
      </div>
  );
}

export default TileNftApp;
