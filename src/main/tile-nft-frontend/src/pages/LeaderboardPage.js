import React from 'react'
import styled from 'styled-components';
import Leaderboard from "../leaderboard/Leaderboard";
import {Col, Row} from "react-bootstrap";
import StyledPage from "./StyledPage";

const LeaderboardPage = () => {
  return (
      <StyledPage>
      <Row>
        <Col />
        <Col xs={8} md={6} >
          <Heading
              className="animate__animated animate__fadeInLeft">Leaderboard</Heading>
          <Leaderboard/>
        </Col>
        <Col />
      </Row>
      </StyledPage>
  );
}

const Heading = styled.h1`
    font-size: clamp(3rem, 5vw, 7vw);
    color: #eee;
    font-weight: 700;
    margin: 0;
    padding: 0;

    user-select: none; /* supported by Chrome and Opera */
   -webkit-user-select: none; /* Safari */
   -khtml-user-select: none; /* Konqueror HTML */
   -moz-user-select: none; /* Firefox */
   -ms-user-select: none; /* Internet Explorer/Edge */
`;

export default LeaderboardPage
