import React from 'react'
import PageHeader from "../styledComponents/PageHeader";
import StyledPage from "../styledComponents/StyledPage";
import {Col, Row} from "react-bootstrap";
import InfoList from "../info/InfoList";

const InfoPage = () => {
  return (
      <StyledPage>
        <Row>
          <Col xs={2} sm={2} md={2} lg={2} xl={2} />
          <Col xs={8} sm={8} md={8} lg={8} xl={8}>
            <PageHeader>Information</PageHeader>
          </Col>
          <Col xs={2} sm={2} md={2} lg={2} xl={2} />
        </Row>
        <InfoList />
      </StyledPage>
  )
}

export default InfoPage
