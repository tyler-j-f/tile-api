import React, {useEffect, useState} from 'react'
import ViewToken from "../view/ViewToken";
import loadTokenAttributes
  from "../updateTileNft/tokenDataLoaders/loadTokenAttributes";
import AttributesTable from "../view/AttributesTable";
import OverallRank from "../view/OverallRank";
import StyledPage from "../styledComponents/StyledPage";
import PageHeader from "../styledComponents/PageHeader";

const ViewPage = () => {

  const [tokenData, setTokenData] = useState({
    tokenId: '',
    tokenAttributes: {},
    isInvalidTokenNumber: false
  });

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
        <PageHeader>View TileNFT</PageHeader>
        <ViewToken
          tokenLoadedCallback={handleTokenLoadedCallback}
          enableUrlSearch={true}
          enableBlockExplorerLink={true}
        />
        {tokenData.tokenId !== '' && !tokenData.isInvalidTokenNumber &&
          <OverallRank
              tokenId={tokenData.tokenId}
          />
        }
        {tokenData.tokenAttributes && Object.keys(tokenData.tokenAttributes).length > 0 &&
          <AttributesTable tokenAttributes={tokenData.tokenAttributes}/>
        }
      </StyledPage>
  )
}

export default ViewPage;
