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
    contractAddress: '',
    contract: null,
    signer: null
  });

  const [ownerAddress, setOwnerAddress] = useState('');

  useEffect(() => {
    loadContractAddress().then(contractAddress => {
      setTokenData({
        ...tokenData,
        contractAddress: contractAddress
      })
    })
  }, []);

  useEffect(
      () => {
        handleLoadContract();
      },
      [tokenData.contractAddress]
  );

  useEffect(() => {
    console.log("useEffect. Before ownerOf", tokenData);
    if (tokenData?.tokenId !== '' && tokenData.contract !== null) {
      console.log("about to call ownerOf", tokenData);
      tokenData.contract.ownerOf(tokenData.tokenId).then(
          (foundOwnerAddress) => {
            console.log("ownerOf result", foundOwnerAddress, tokenData);
            setOwnerAddress(foundOwnerAddress);
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

  const handleLoadContract = () => {
    if (tokenData.contractAddress !== '') {
      let contract = new ethers.Contract(tokenData.contractAddress, TileContract.abi, provider);
      setTokenData({
        ...tokenData,
        contract: contract
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

  const shouldRenderBlockExplorerTokenLink = () => tokenData.blockExplorerUrl !== '' && !tokenData.isInvalidTokenNumber;

  const shouldRenderBlockExplorerAccountLink = () => ownerAddress !== '' && !tokenData.isInvalidTokenNumber;

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
                      Token owner address:&nbsp;
                      <br/>
                      <StyledAnchor href={`https://rinkeby.etherscan.io/address/${ownerAddress}#tokentxnsErc721`} target="_blank" >
                        {ownerAddress}
                      </StyledAnchor>
                    </StyledText>
                  </li>
                }
                {!shouldRenderBlockExplorerAccountLink() && (
                    <li>
                      <StyledText>
                        The current token owner address can be found via the block explorer or Opensea links above.
                      </StyledText>
                    </li>
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
