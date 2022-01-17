import React from 'react'
import styled from 'styled-components';
import Leaderboard from "../leaderboard/Leaderboard";
import {Col, Row} from "react-bootstrap";
import StyledPage from "./StyledPage";
import PageHeader from "./PageHeader";

const LeaderboardPage = () => {
  return (
      <StyledPage>
      <Row>
        <Col />
        <Col xs={8} md={6} >
          <PageHeader
              className="leaderboardPageHeader animate__animated animate__fadeInLeft"
          >
            Leaderboard
          </PageHeader>
          <Leaderboard/>
        </Col>
        <Col />
      </Row>
      </StyledPage>
  );
}

export default LeaderboardPage
