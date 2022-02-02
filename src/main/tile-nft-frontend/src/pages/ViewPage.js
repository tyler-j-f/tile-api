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
import {ethers} from "ethers";
import TileContract from "../contractsJson/Tile.json";
import {useEthers} from "@usedapp/core";
import loadContractAddress from "../view/loadContractAddress";

const ViewPage = () => {
  const { library: provider } = useEthers()
  const [tokenData, setTokenData] = useState({
    tokenId: '',
    tokenAttributes: {},
    isInvalidTokenNumber: false,
    blockExplorerUrl: '',
    openSeaData: {},
    contractAddress: '',
    tileContract: null,
    signer: null,
    ownerAddress: ''
  });

  useEffect(
      () => {
        console.log("DEBUG: useEffect", tokenData);
        loadContractAddress().then(contractAddress => {
          console.log("DEBUG: loadContractAddress result", contractAddress, tokenData);
          setTokenData({
            ...tokenData,
            contractAddress: contractAddress
          });
        })
      },
      []
  );

  useEffect(
      () => {
        console.log("DEBUG: useEffect handleProviderAndSigner", tokenData);
        if (provider && tokenData.contractAddress !== '') {
          handleProviderAndSigner();
        }
      },
      [provider, tokenData.contractAddress]
  );

  useEffect(
      () => {
        console.log("DEBUG: useEffect ownerOf", tokenData);
        if (tokenData.tokenId !== '' && tokenData.tileContract !== null && tokenData.signer !== null) {
          tokenData.tileContract.ownerOf(tokenData.tokenId).then(
              tokenOwner => {
                console.log("DEBUG: useEffect ownerOf result", tokenOwner, tokenData);
                setTokenData({
                  ...tokenData,
                  ownerAddress: tokenOwner
                })
              }
          ).catch(e => {
            console.log("ERROR CAUGHT!!!", e);
          });
        }
      },
      [tokenData.tileContract, tokenData.signer, tokenData.tokenId]
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

  const handleProviderAndSigner = () => {
    let tileContract = new ethers.Contract(tokenData.contractAddress, TileContract.abi, provider);
    let signer = provider.getSigner();
    console.log("DEBUG: loadContractAddress result", tileContract, signer, tokenData);
    // The MetaMask plugin also allows signing transactions to
    // send ether and pay to change state within the blockchain.
    // For this, you need the account signer...
    setTokenData({
      ...tokenData,
      tileContract: tileContract,
      signer: signer
    });
  }

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
                    </>
                )}
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
                              To be improved. A circular version will be available soon (to fits the twitter profile picture aesthetic).
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
