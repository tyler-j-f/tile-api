import React from 'react'
import StyledPage from "../styledComponents/StyledPage";
import PageHeader from "../styledComponents/PageHeader";
import StyledText from "../styledComponents/StyledText";
import PageSubHeader from "../styledComponents/PageSubHeader";
import styled from "styled-components";

const OpenSeaPage = () => {
  return (
      <StyledPage>
        <PageHeader>OpenSea</PageHeader>
        <div>
          <PageSubHeader>Buy new TileNFTs from the initial sale on OpenSea!!</PageSubHeader>
          <ul>
            <li>
              <StyledText>
                <StyledAnchor href={"opensea.org"}>Link to</StyledAnchor>
                &nbsp;the OpenSea TileNFT sale.
              </StyledText>
            </li>
            <li>
              <StyledText>
                <StyledAnchor href={"opensea.org"}>Link to</StyledAnchor>
                &nbsp;view TileNFTs collection, for buying/selling on OpenSea.
              </StyledText>
            </li>
          </ul>
        </div>
      </StyledPage>
  )
}

const StyledAnchor = styled.a`
  width: 275px;
  text-align: center;
  color: #9F566F;
`;

export default OpenSeaPage
