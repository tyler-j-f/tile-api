import React, {useEffect, useState} from 'react'
import ViewToken from "../view/ViewToken";
import loadTokenAttributes
  from "../updateTileNft/tokenDataLoaders/loadTokenAttributes";
import AttributesTable from "../view/AttributesTable";
import OverallRank from "../view/OverallRank";
import StyledPage from "../styledComponents/StyledPage";
import PageHeader from "../styledComponents/PageHeader";
import {Col, Row} from "react-bootstrap";
import StyledText from "../styledComponents/StyledText";
import styled from "styled-components";
import PageSubHeader from "../styledComponents/PageSubHeader";
import loadOpenSeaData
  from "../updateTileNft/tokenDataLoaders/loadOpenSeaData";

const ViewPage = () => {

  const [tokenData, setTokenData] = useState({
    tokenId: '',
    tokenAttributes: {},
    isInvalidTokenNumber: false,
    blockExplorerUrl: '',
    openSeaData: {}
  });

  useEffect(() => {
    if (tokenData?.tokenId !== '') {
      loadTokenAttributes({tokenId: tokenData.tokenId}).then(loadTokenAttributesResult => {
        loadOpenSeaData({tokenId: tokenData.tokenId}).then(loadOpenSeaDataResult => {
          setTokenData({
            ...tokenData,
            tokenAttributes: loadTokenAttributesResult,
            openSeaData: loadOpenSeaDataResult
          })
        })
      });
    }
  }, [tokenData.tokenId]);

  const handleTokenLoadedCallback = ({tokenId, isInvalidTokenNumber, blockExplorerUrl}) => {
    setTokenData({
      ...tokenData,
      tokenId,
      isInvalidTokenNumber,
      blockExplorerUrl
    });
  }

  const shouldRenderAttributesTable = () => tokenData.tokenAttributes &&
      Object.keys(tokenData.tokenAttributes).length > 0 && !tokenData.isInvalidTokenNumber;

  const shouldRenderOpenSeaLinks = () => tokenData.openSeaData &&
      Object.keys(tokenData.openSeaData).length > 0 && !tokenData.isInvalidTokenNumber;

  const isValidLockedInTokenId = () => tokenData.tokenId !== '' && !tokenData.isInvalidTokenNumber;

  const shouldRenderBlockExplorerLink = () => tokenData.blockExplorerUrl !== '' && !tokenData.isInvalidTokenNumber;

  const getTwitterImageUrl = () => `${window.location.origin}/api/image/twitter/tile/get/${tokenData.tokenId}`

  return (
      <StyledPage>
        <Row>
          <Col xs={2} sm={2} md={2} lg={2} xl={2} />
          <Col xs={8} sm={8} md={8} lg={8} xl={8} className="text-center" >
            <PageHeader>View TileNFT</PageHeader>
            <ViewToken
                tokenLoadedCallback={handleTokenLoadedCallback}
                enableUrlSearch={true}
                enableBlockExplorerLink={true}
            />
          </Col>
          <Col xs={2} sm={2} md={2} lg={2} xl={2} />
        </Row>
        {(isValidLockedInTokenId() || shouldRenderBlockExplorerLink()) &&
          <Row>
            <Col xs={2} sm={2} md={2} lg={2} xl={2} />
            <Col xs={8} sm={8} md={8} lg={8} xl={8} className="text-center" >
              <StyledList>
                {isValidLockedInTokenId() &&
                  <li>
                    <OverallRank
                        tokenId={tokenData.tokenId}
                    />
                  </li>
                }
                {shouldRenderBlockExplorerLink() &&
                  <li>
                    <StyledText>
                      <StyledAnchor href={tokenData.blockExplorerUrl} target="_blank" >
                        View token
                      </StyledAnchor>
                      &nbsp;on block explorer.
                    </StyledText>
                  </li>
                }
                {shouldRenderOpenSeaLinks() && (
                    <>
                      <li>
                        <StyledText>
                          <StyledAnchor href={tokenData.openSeaData.tokenUrl} target="_blank" >
                            View token
                          </StyledAnchor>
                          &nbsp;on OpenSea.
                        </StyledText>
                      </li>
                      <li>
                        <StyledText>
                          <StyledAnchor href={tokenData.openSeaData.saleUrl} target="_blank" >
                            View token sale
                          </StyledAnchor>
                          &nbsp;on OpenSea.
                        </StyledText>
                      </li>
                    </>
                )}
                {isValidLockedInTokenId() && (
                    <li>
                      <StyledText>
                        <StyledAnchor href={getTwitterImageUrl()} target="_blank" >
                          View Twitter
                        </StyledAnchor>
                        &nbsp;version of image.
                      </StyledText>
                    </li>
                )}
              </StyledList>
            </Col>
            <Col xs={2} sm={2} md={2} lg={2} xl={2} />
          </Row>
        }
        {shouldRenderAttributesTable() &&
          <Row>
            <Col xs={2} sm={2} md={2} lg={2} xl={2} />
            <Col xs={8} sm={8} md={8} lg={8} xl={8} className="text-center" >
              <PageSubHeader>Token Attributes</PageSubHeader>
              <AttributesTable tokenAttributes={tokenData.tokenAttributes}/>
            </Col>
            <Col xs={2} sm={2} md={2} lg={2} xl={2} />
          </Row>
        }
      </StyledPage>
  )
}


const StyledAnchor = styled.a`
  width: 275px;
  text-align: center;
  color: #9F566F;
`;


const StyledList = styled.ul`
  width: 275px;
`;

export default ViewPage;
