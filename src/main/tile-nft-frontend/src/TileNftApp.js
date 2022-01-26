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
import InfoPage from './pages/InfoPage'
import LeaderboardPage from "./pages/LeaderboardPage";
import MergePage from "./pages/MergePage";
import {Container, Navbar, Row} from "react-bootstrap";
import styled from "styled-components";
import CustomizeTileNftPage from "./pages/CustomizeTileNftPage";
import StyledErrorText from "./styledComponents/StyledErrorText";
import OpenSeaPage from "./pages/OpenSeaPage";

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

  const getHeader = () => (
      <Row>
        <StyledDiv>
          <Navbar
              fixed={"top"}
              sticky={"top"}
          >
            <Toggle handleNavToggle={handleNavToggle}/>
          </Navbar>
        </StyledDiv>
      </Row>
  );

  const getRouter = () => (
      <Router>
        {navToggled ? <Menu handleNavToggle={handleNavToggle} /> : null }
        <Routes>
          <Route exact path="/" element={<HomePage/>}/>
          <Route exact path="/leaderboard" element={<LeaderboardPage/>}/>
          <Route exact path="/view" element={<ViewPage/>}/>
          <Route exact path="/openSea" element={<OpenSeaPage/>}/>
          <Route exact path="/customize" element={<CustomizeTileNftPage />}/>
          <Route exact path="/merge" element={<MergePage />}/>
          <Route exact path="/info" element={<InfoPage/>}/>
        </Routes>
      </Router>
  );

  const getErrorText = () => (
      <Row>
        {hasNotFoundError ? getNotFoundErrorText() : null}
        {hasInternalError ? getInternalErrorText() : null}
      </Row>
  );

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

  return (
      <div className="App">
        <Container fluid>
          {!navToggled && getHeader()}
          {hasNotFoundError || hasInternalError && getErrorText()}
          {getRouter()}
        </Container>
      </div>
  );
}

const StyledDiv =
    styled.div`
    background-color: #00ACE6
    `;

export default TileNftApp;
