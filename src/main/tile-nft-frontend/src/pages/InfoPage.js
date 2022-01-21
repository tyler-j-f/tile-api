import React from 'react'
import styled from 'styled-components';
import PageHeader from "../styledComponents/PageHeader";
import StyledPage from "../styledComponents/StyledPage";

const InfoPage = () => {
  return (
      <StyledPage>
        <PageHeader>Contact Page</PageHeader>
        <Content>
          <h1>👋 Hey there </h1>
          <p>Info Info Info</p>
        </Content>
      </StyledPage>
  )
}

const Content = styled.div`
    color: #eee;
    font-size: clamp(1.5rem, 2vw, 4vw);

    a {
        color: skyblue;
        text-decoration: none;
    }
`;

export default InfoPage
