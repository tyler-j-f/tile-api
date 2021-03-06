import React, {useEffect, useState} from 'react'
import StyledPage from "../styledComponents/StyledPage";
import PageHeader from "../styledComponents/PageHeader";
import StyledText from "../styledComponents/StyledText";
import PageSubHeader from "../styledComponents/PageSubHeader";
import styled from "styled-components";
import loadOpenSeaData from "../customizeTileNft/tokenDataLoaders/loadOpenSeaData";
import StyledAnchor from "../styledComponents/styledAnchor";

const OpenSeaPage = () => {

  const [openSeaData, setOpenSeaData] = useState({
    saleUrl: '',
    collectionUrl: ''
  });

  const OPEN_SEA_URL = "https://opensea.io/";

  useEffect(() => {
    // Load token 1 related data, thew token id doe not matter.
    loadOpenSeaData({tokenId: 1}).then(loadOpenSeaDataResult => {
      setOpenSeaData({
        ...openSeaData,
        ...loadOpenSeaDataResult
      })
    })
  }, []);

  return (
      <StyledPage>
        <PageHeader>Buy TileNFTs On&nbsp;<StyledAnchor href={OPEN_SEA_URL} >OpenSea</StyledAnchor></PageHeader>
        <div>
          {(openSeaData.saleUrl !== '' || openSeaData.collectionUrl !== '') && (
              <ul>
                <li>
                  <StyledText>
                    <StyledAnchor href={openSeaData.saleUrl} target="_blank" >Link to</StyledAnchor>
                    &nbsp;the OpenSea TileNFT sale.
                  </StyledText>
                </li>
                <li>
                  <StyledText>
                    <StyledAnchor href={openSeaData.collectionUrl} target="_blank" >Link to</StyledAnchor>
                    &nbsp;view TileNFTs OpenSea collection. For buying/selling on OpenSea.
                  </StyledText>
                </li>
              </ul>
          )}
        </div>
      </StyledPage>
  )
}

export default OpenSeaPage
