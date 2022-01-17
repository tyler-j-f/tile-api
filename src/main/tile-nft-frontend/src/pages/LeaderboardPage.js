import React from 'react'
import Leaderboard from "../leaderboard/Leaderboard";
import {Col, Row} from "react-bootstrap";
import StyledPage from "../styledComponents/StyledPage";
import PageHeader from "../styledComponents/PageHeader";

const LeaderboardPage = () => {
  return (
      <StyledPage>
      <Row>
        <Col xs={2} sm={2} md={2} lg={2} xl={2} />
        <Col xs={8} sm={8} md={8} lg={8} xl={8} >
          <PageHeader
              className="animate__animated animate__fadeInLeft"
          >
            Leaderboard
          </PageHeader>
          <Leaderboard/>
        </Col>
        <Col xs={2} sm={2} md={2} lg={2} xl={2} />
      </Row>
      </StyledPage>
  );
}

export default LeaderboardPage
