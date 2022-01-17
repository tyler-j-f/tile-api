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

const ViewPage = () => {

  const [tokenData, setTokenData] = useState({
    tokenId: '',
    tokenAttributes: {},
    isInvalidTokenNumber: false,
    blockExplorerUrl: ''
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

  const handleTokenLoadedCallback = ({tokenId, isInvalidTokenNumber, blockExplorerUrl}) => {
    setTokenData({
      ...tokenData,
      tokenId,
      isInvalidTokenNumber,
      blockExplorerUrl
    });
  }

  const shouldRenderAttributesTable = () => tokenData.tokenAttributes &&
      Object.keys(tokenData.tokenAttributes).length > 0;

  const shouldRenderOverallRank = () => tokenData.tokenId !== '' && !tokenData.isInvalidTokenNumber;

  const shouldRenderBlockExplorerLink = () => tokenData.blockExplorerUrl !== '';

  return (
      <StyledPage>
        <Row>
          <Col xs={2} sm={2} md={2} lg={2} xl={2} />
          <Col xs={8} sm={8} md={8} lg={8} xl={8} >
            <PageHeader>View TileNFT</PageHeader>
            <ViewToken
                tokenLoadedCallback={handleTokenLoadedCallback}
                enableUrlSearch={true}
                enableBlockExplorerLink={true}
            />
          </Col>
          <Col xs={2} sm={2} md={2} lg={2} xl={2} />
        </Row>
        {(shouldRenderOverallRank() || shouldRenderBlockExplorerLink()) &&
          <Row>
            <Col />
            <Col >
              <StyledList>
                {shouldRenderOverallRank() &&
                  <li>
                    <OverallRank
                        tokenId={tokenData.tokenId}
                    />
                  </li>
                }
                {shouldRenderBlockExplorerLink() &&
                  <li>
                    <StyledText>
                      <StyledAnchor href={tokenData.blockExplorerUrl} >
                        View Token
                      </StyledAnchor>
                      &nbsp;On Block Explorer
                    </StyledText>
                  </li>
                }
              </StyledList>
            </Col>
            <Col />
          </Row>
        }
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


const StyledAnchor = styled.a`
  width: 275px;
  text-align: center;
  color: #9F566F;
`;


const StyledList = styled.ul`
  width: 275px;
`;

export default ViewPage;
