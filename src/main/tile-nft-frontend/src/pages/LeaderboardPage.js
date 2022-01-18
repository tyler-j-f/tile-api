import React from 'react'
import Leaderboard from "../leaderboard/Leaderboard";
import {Col, Row} from "react-bootstrap";
import StyledPage from "../styledComponents/StyledPage";
import PageHeader from "../styledComponents/PageHeader";

const LeaderboardPage = () => {
  return (
      <StyledPage>
      <Row>
        <PageHeader
            className="animate__animated animate__fadeInLeft text-center"
        >
          Leaderboard
        </PageHeader>
      </Row>
      <Row>
        <Col xs={2} sm={2} md={2} lg={0} xl={0} />
        <Col xs={8} sm={8} md={8} lg={12} xl={12} >
          <Leaderboard/>
        </Col>
        <Col xs={2} sm={2} md={2} lg={0} xl={0} />
      </Row>
      </StyledPage>
  );
}

export default LeaderboardPage
