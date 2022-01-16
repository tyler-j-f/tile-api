// style
import './css/App.css';
import { useState, useEffect } from 'react';
import {
  BrowserRouter as Router,
  Route,
  Routes
} from 'react-router-dom';
// components
import Toggle from './components/Toggle';
import Menu from './components/Menu';
// pages
import HomePage from './pages/HomePage';
import ViewPage from './pages/ViewPage';
import ContactPage from './pages/ContactPage'
import LeaderboardPage from "./pages/LeaderboardPage";
import MergePage from "./pages/MergePage";
import {Col, Container, Navbar, Row} from "react-bootstrap";
import styled from "styled-components";
import UpdateTileNftPage from "./pages/UpdateTileNftPage";

function TileNftApp() {

  const [navToggled, setNavToggled] = useState(false);
  const [hasNotFoundError, setHasNotFoundError] = useState(false);
  const [hasInternalError, setHasInternalError] = useState(false);

  const handleNavToggle = () => {
    setNavToggled(!navToggled);
  }

  useEffect(() => {
    let errorCode = new URL(window.location.href).searchParams.get('errorCode');
    if (errorCode === null) {
      setHasNotFoundError(false);
      setHasInternalError(false);
      return;
    }
    console.log('errorCode: ' + errorCode);
    if (errorCode === '404' && !hasNotFoundError) {
      setHasNotFoundError(!hasNotFoundError);
      return;
    }
    if (errorCode === '500' && !hasInternalError) {
      setHasInternalError(!hasInternalError);
      return;
    }
    return;
  }, [hasNotFoundError, hasInternalError]);

  return (
      <div className="App">
        <Container fluid>
          <Row>
            <Navbar
                fixed={"top"}
                sticky={"top"}
            >
              <Toggle handleNavToggle={handleNavToggle}/>
            </Navbar>
          </Row>
          <Row>
            {hasNotFoundError ? getNotFoundErrorText() : null}
            {hasInternalError ? getInternalErrorText() : null}
          </Row>
          <Router>
            { navToggled ? <Menu handleNavToggle={handleNavToggle} /> : null }
            <Routes>
              <Route exact path="/" element={<HomePage/>}/>
              <Route exact path="/leaderboard" element={<LeaderboardPage/>}/>
              <Route exact path="/view" element={<ViewPage/>}/>
              <Route exact path="/update" element={<UpdateTileNftPage />}/>
              <Route exact path="/merge" element={<MergePage />}/>
              <Route exact path="/contact" element={<ContactPage/>}/>
            </Routes>
          </Router>
        </Container>
      </div>
  );
}

const getNotFoundErrorText = () => {
  return (
      <StyledErrorText>Error! Page not found. Please check the URL is correct and then retry.</StyledErrorText>
  )
}

const getInternalErrorText = () => {
  return (
      <StyledErrorText>Internal error! Please try again later.</StyledErrorText>
  )
}

const StyledErrorText =
    styled.p`
    display: block;
    color: #FF4500;
    text-align: center;
    margin-top: 50px
    `;

export default TileNftApp;
