import React, {useEffect, useState} from 'react'
import StyledPage from "../styledComponents/StyledPage";
import PageHeader from "../styledComponents/PageHeader";
import StyledText from "../styledComponents/StyledText";
import PageSubHeader from "../styledComponents/PageSubHeader";
import styled from "styled-components";
import loadOpenSeaData from "../updateTileNft/tokenDataLoaders/loadOpenSeaData";

const OpenSeaPage = () => {

  const [openSeaData, setOpenSeaData] = useState({
    saleUrl: '',
    collectionUrl: ''
  });

  useEffect(() => {
    // Load token 1 related data, thew token id doe not matter.
    loadOpenSeaData({tokenId: 1}).then(loadOpenSeaDataResult => {
      console.log("Debug loadOpenSeaDataResult", loadOpenSeaDataResult);
      setOpenSeaData({
        ...openSeaData,
        ...loadOpenSeaDataResult
      })
    })
  }, []);

  console.debug("debug render", openSeaData, openSeaData.saleUrl !== '', openSeaData.collectionUrl !== '');
  return (
      <StyledPage>
        <PageHeader>OpenSea</PageHeader>
        <div>
          <PageSubHeader>Buy new TileNFTs from the initial sale on OpenSea!!</PageSubHeader>
          {(openSeaData.saleUrl !== '' || openSeaData.collectionUrl !== '') && (
              <ul>
                <li>
                  <StyledText>
                    <StyledAnchor href={openSeaData.saleUrl}>Link to</StyledAnchor>
                    &nbsp;the OpenSea TileNFT sale.
                  </StyledText>
                </li>
                <li>
                  <StyledText>
                    <StyledAnchor href={openSeaData.collectionUrl}>Link to</StyledAnchor>
                    &nbsp;view TileNFTs OpenSea collection. For buying/selling the tokens on OpenSea.
                  </StyledText>
                </li>
              </ul>
          )}
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
