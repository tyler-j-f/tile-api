// style
import './css/App.css';
import TileNftHeader from "./header/TileNftHeader";
import { useState, useEffect } from 'react';
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
import styled from "styled-components";

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
  });

  return (
      <div className="App">
        <Container fluid>
          <Row>
            <Col xs={8} sm={8} md={8} lg={8} xl={8} xxl={8} />
            <Col xs={4} sm={4} md={4} lg={4} xl={4} xxl={4} >
              <Toggle handleNavToggle={handleNavToggle}/>
            </Col>
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
