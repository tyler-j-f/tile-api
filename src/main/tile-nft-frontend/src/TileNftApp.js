// style
import './css/App.css';
import TileNftHeader from "./header/TileNftHeader";
import { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
// components
import Toggle from './components/Toggle';
import Menu from './components/Menu';
// pages
import HomePage from './pages/HomePage';
import ViewPage from './pages/ViewPage';
import ContactPage from './pages/ContactPage'
import LeaderboardPage from "./pages/LeaderboardPage";
import {Col, Container, Row} from "react-bootstrap";

function TileNftApp() {

  const [navToggled, setNavToggled] = useState(false);

  const handleNavToggle = () => {
    setNavToggled(!navToggled);
  }

  return (
      <div className="App">
        <Container fluid>
          <Row>
            <Col xs={8} sm={8} md={8} lg={8} xl={8} xxl={8} />
            <Col xs={4} sm={4} md={4} lg={4} xl={4} xxl={4} >
              <Toggle handleNavToggle={handleNavToggle}/>
            </Col>
          </Row>
          <Router>
            { navToggled ? <Menu handleNavToggle={handleNavToggle} /> : null }
            <Routes>
              <Route exact path="/" element={<HomePage/>}/>
              <Route exact path="/leaderboard" element={<LeaderboardPage/>}/>
              <Route exact path="/view" element={<ViewPage/>}/>
              <Route exact path="/contact" element={<ContactPage/>}/>
            </Routes>
          </Router>
        </Container>
      </div>
  );
}

export default TileNftApp;
