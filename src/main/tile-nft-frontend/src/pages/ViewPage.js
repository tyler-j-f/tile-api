import React, {useEffect, useState} from 'react'
import ViewToken from "../view/ViewToken";
import loadTokenAttributes
  from "../updateTileNft/tokenDataLoaders/loadTokenAttributes";
import AttributesTable from "../view/AttributesTable";
import OverallRank from "../view/OverallRank";
import StyledPage from "../styledComponents/StyledPage";
import PageHeader from "../styledComponents/PageHeader";
import {Col, Row} from "react-bootstrap";

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

  const shouldRenderAttributesTable = () => tokenData.tokenAttributes &&
      Object.keys(tokenData.tokenAttributes).length > 0;

  return (
      <StyledPage>
        <Row>
          <Col xs={2} />
          <Col xs={10} >
            <PageHeader>View TileNFT</PageHeader>
            <ViewToken
                tokenLoadedCallback={handleTokenLoadedCallback}
                enableUrlSearch={true}
                enableBlockExplorerLink={true}
            />
          </Col>
          <Col xs={2} />
        </Row>
        <Row>
          <Col />
          <Col >
            {tokenData.tokenId !== '' && !tokenData.isInvalidTokenNumber &&
              <OverallRank
                  tokenId={tokenData.tokenId}
              />
            }
          </Col>
          <Col />
        </Row>
        {shouldRenderAttributesTable() &&
          <Row>
            <Col />
            <Col >
              <AttributesTable tokenAttributes={tokenData.tokenAttributes}/>
            </Col>
            <Col />
          </Row>
        }
      </StyledPage>
  )
}

export default ViewPage;
