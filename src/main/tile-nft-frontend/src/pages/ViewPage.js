import React, {useEffect, useState} from 'react'
import ViewToken from "../view/ViewToken";
import loadTokenAttributes
  from "../customizeTileNft/tokenDataLoaders/loadTokenAttributes";
import AttributesTable from "../view/AttributesTable";
import OverallRank from "../view/OverallRank";
import StyledPage from "../styledComponents/StyledPage";
import PageHeader from "../styledComponents/PageHeader";
import {Col, Row} from "react-bootstrap";
import StyledText from "../styledComponents/StyledText";
import styled from "styled-components";
import PageSubHeader from "../styledComponents/PageSubHeader";
import loadOpenSeaData
  from "../customizeTileNft/tokenDataLoaders/loadOpenSeaData";
import StyledAnchor from "../styledComponents/styledAnchor";
import loadContractAddress from "../view/loadContractAddress";

const ViewPage = () => {
  const [tokenOwnerAddress, setTokenOwnerAddress] = useState('');

  const [tokenData, setTokenData] = useState({
    tokenId: '',
    tokenAttributes: {},
    isInvalidTokenNumber: false,
    blockExplorerUrl: '',
    openSeaData: {}
  });

  useEffect(
      () => {
        if (tokenData.tokenId !== '') {
          loadContractAddress(tokenData.tokenId).then(
              tokenOwner => setTokenOwnerAddress(tokenOwner)
          ).catch(e => {
            console.log("ERROR CAUGHT!!!", e);
            setTokenOwnerAddress('');
          });
        }
      },
      [tokenData.tokenId]
  );

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

  const shouldRenderOwnerAddress = () => tokenOwnerAddress!== '' && tokenData.tokenId !== '';

  const getTwitterImageUrl = () => `${window.location.origin}/api/image/twitter/tile/get/${tokenData.tokenId}`

  const getInitialTokenId = () => {
   let url = new URL(window.location.href).searchParams.get('tokenId');
   return !!url ? url : '';
  };

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
                initialTokenId={getInitialTokenId()}
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
                    </>
                )}
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
                {shouldRenderOwnerAddress() &&
                  <li>
                    <StyledText>
                      Owner Address:&nbsp;
                      <br/>
                      <StyledAnchor href={`https://rinkeby.etherscan.io/address/${tokenOwnerAddress}`} target="_blank" >
                        {tokenOwnerAddress}
                      </StyledAnchor>
                    </StyledText>
                  </li>
                }
                {isValidLockedInTokenId() && (
                    <li>
                      <StyledText>
                        <StyledAnchor href={getTwitterImageUrl()} target="_blank" >
                          View Twitter
                        </StyledAnchor>
                        &nbsp;version of image.
                        <StyledList>
                          <li>
                            <StyledText>
                              To be improved. A circular version will be available soon (to better fit the twitter profile picture aesthetic).
                            </StyledText>
                          </li>
                        </StyledList>
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

const StyledList = styled.ul`
  width: 275px;
`;

export default ViewPage;
