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
import {useEthers} from "@usedapp/core";
import {ethers} from "ethers";
import TileContract from "../contractsJson/Tile.json";
import loadContractAddress from "../view/loadContractAddress";

const ViewPage = () => {

  const { library: provider } = useEthers();
  const [tokenData, setTokenData] = useState({
    tokenId: '',
    tokenAttributes: {},
    isInvalidTokenNumber: false,
    blockExplorerUrl: '',
    openSeaData: {},
    ownerAddress: "",
    contractAddress: '',
    contract: null,
    signer: null
  });

  useEffect(() => {
    console.log("DEBUG: useEffect. about to loadContractAddress", tokenData);
    loadContractAddress().then(contractAddress => {
      console.log("DEBUG: loadContractAddress result", contractAddress, tokenData);
      setTokenData({
        ...tokenData,
        contractAddress: contractAddress
      })
    })
  }, []);

  useEffect(
      () => {
        console.log("DEBUG: useEffect. about to handleProviderAndSigner", tokenData)
        handleProviderAndSigner();
      },
      [tokenData.contractAddress]
  );

  useEffect(() => {
    console.log("DEBUG: useEffect. about to call ownerOf 1", tokenData);
    if (tokenData?.tokenId !== '' && tokenData.contract !== null) {
      console.log("DEBUG: useEffect. about to call ownerOf 2");
      tokenData.contract.ownerOf(tokenData.tokenId).then(
          (ownerAddress) => {
            console.log("ownerOf result", ownerAddress, tokenData);
            setTokenData({
              ...tokenData,
              ownerAddress: ownerAddress
            });
          }
      )
    }
  }, [tokenData.contract, tokenData.tokenId]);

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
    console.log("DEBUG: handleProviderAndSigner", provider, tokenData.contractAddress , tokenData);
    if (provider && tokenData.contractAddress !== '') {
      console.log("DEBUG: handleProviderAndSigner. Inside closure");
      let contract = new ethers.Contract(tokenData.contractAddress, TileContract.abi, provider);
      let signer = provider.getSigner();
      console.log("DEBUG: handleProviderAndSigner. set data", tokenData)
      setTokenData({
        ...tokenData,
        contract: contract,
        signer: signer
      });
    }
  }

  const handleTokenLoadedCallback = ({tokenId, isInvalidTokenNumber, blockExplorerUrl}) => {
    setTokenData({
      ...tokenData,
      tokenId,
      isInvalidTokenNumber,
      blockExplorerUrl
    });
  }

  const shouldRenderAttributesTable = () => tokenData.tokenId !== '' && tokenData.tokenAttributes &&
      Object.keys(tokenData.tokenAttributes).length > 0 && !tokenData.isInvalidTokenNumber;

  const shouldRenderOpenSeaLinks = () => tokenData.openSeaData &&
      Object.keys(tokenData.openSeaData).length > 0 && !tokenData.isInvalidTokenNumber;

  const isValidLockedInTokenId = () => tokenData.tokenId !== '' && !tokenData.isInvalidTokenNumber;

  const shouldRenderBlockExplorerTokenLink = () => tokenData.ownerAddress !== '' && !tokenData.isInvalidTokenNumber;

  const shouldRenderBlockExplorerAccountLink = () => tokenData.blockExplorerUrl !== '' && !tokenData.isInvalidTokenNumber;

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
        {(isValidLockedInTokenId() || shouldRenderBlockExplorerTokenLink() || shouldRenderBlockExplorerAccountLink()) &&
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
                {shouldRenderBlockExplorerTokenLink() &&
                  <li>
                    <StyledText>
                      <StyledAnchor href={tokenData.blockExplorerUrl} target="_blank" >
                        View token
                      </StyledAnchor>
                      &nbsp;on block explorer.
                    </StyledText>
                  </li>
                }
                {shouldRenderBlockExplorerAccountLink() &&
                  <li>
                    <StyledText>
                      <StyledAnchor href={tokenData.blockExplorerUrl} target="_blank" >
                        View token
                      </StyledAnchor>
                      &nbsp;on block explorer.
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
