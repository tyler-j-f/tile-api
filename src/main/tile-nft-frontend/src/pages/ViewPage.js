import React, {useEffect, useState} from 'react'
import styled from 'styled-components';
import ViewToken from "../view/ViewToken";
import loadTokenAttributes
  from "../updateTileNft/tokenDataLoaders/loadTokenAttributes";

const ViewPage = () => {

  const [tokenData, setTokenData] = useState({
    tokenId: '',
    tokenAttributes: {}
  });

  useEffect(() => {
    if (tokenData?.tokenId !== '') {
      loadTokenAttributes({tokenId: tokenData.tokenId}).then(result => {
        console.log("tokenAttributes", result);
        setTokenData({
          ...tokenData,
          tokenAttributes: result
        })
      })
    }
  }, [tokenData.tokenId]);

  const handleTokenLoadedCallback = ({tokenId}) => {
    setTokenData({
      ...tokenData,
      tokenId
    });
  }

  const logIt = () => {
    console.log("debug tokenData.tokenAttributes", tokenData.tokenAttributes);
    return false;
  }


  return (
      <StyledPage>
        <Heading className="animate__animated animate__fadeInLeft">View TileNft</Heading>
        <ViewToken
          tokenLoadedCallback={handleTokenLoadedCallback}
        />
        {logIt() && <StyledText>logIt</StyledText>}
        {tokenData.tokenAttributes && Object.keys(tokenData.tokenAttributes).length && <StyledText>Attributes found</StyledText>}
      </StyledPage>
  )
}

const StyledPage = styled.div`
    min-height: 100vh;
    width: 100vw;
    background-color: #282c34;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;

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

const StyledText =
  styled.p`
  color: white;
  font-weight: bold;
`;

export default ViewPage;
