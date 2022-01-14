import React, {useEffect, useState} from 'react'
import styled from 'styled-components';
import ViewToken from "../view/ViewToken";
import loadTokenAttributes
  from "../updateTileNft/tokenDataLoaders/loadTokenAttributes";
import AttributesTable from "../view/AttributesTable";
import OverallRank from "../view/OverallRank";

const ViewPage = () => {

  const [tokenData, setTokenData] = useState({
    tokenId: '',
    tokenAttributes: {},
    isInvalidTokenNumber: false
  });

  useEffect(() => {
    let urlTokenId = new URL(window.location.href).searchParams.get('tokenId');
    console.log("debug urlTokenId", urlTokenId);
    if (urlTokenId !== null) {
      console.log("debug urlTokenId 2", urlTokenId);
      setTokenData({
        ...tokenData,
        tokenId: urlTokenId
      });
    }
  }, []);

  useEffect(() => {
    if (tokenData?.tokenId !== '') {
      loadTokenAttributes({tokenId: tokenData.tokenId}).then(result => {
        setTokenData({
          ...tokenData,
          tokenAttributes: result
        })
      })
    }
  }, [tokenData.tokenId]);

  const handleTokenLoadedCallback = ({tokenId, isInvalidTokenNumber}) => {
    setTokenData({
      ...tokenData,
      tokenId,
      isInvalidTokenNumber
    });
  }

  return (
      <StyledPage>
        <Heading className="animate__animated animate__fadeInLeft">View TileNFT</Heading>
        <ViewToken
          tokenLoadedCallback={handleTokenLoadedCallback}
        />
        {tokenData.tokenId !== '' && !tokenData.isInvalidTokenNumber &&
          <OverallRank
              tokenId={tokenData.tokenId}
          />
        }
        {tokenData.tokenAttributes && Object.keys(tokenData.tokenAttributes).length &&
          <AttributesTable tokenAttributes={tokenData.tokenAttributes}/>
        }
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

export default ViewPage;
