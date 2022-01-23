import MetadataSetTxWrapper from "./MetadataSetTxWrapper";
import {useEthers} from "@usedapp/core";
import ViewToken from "../view/ViewToken";
import React, {useState} from "react";
import {Button, Col, Row} from "react-bootstrap";
import PageSubHeader from "../styledComponents/PageSubHeader";

const noop = () => {};

const UpdateTileNft = ({
  successCallback = noop,
  metadataMapper = noop,
  dataToSetGetter = noop,
  getMetadataUpdatedTokenUrl = noop,
  loadDataToUpdateRelatedData = noop,
  dataToSetIndex = null,
  SelectorSection = null,
  attributesRegex = '',
  numberOfEntriesToSet = 4
}) => {
  const {account} = useEthers();
  const [tokenId, setTokenId] = useState('');
  const [contractAddress, setContractAddress] = useState('');
  const [metadataToUpdate, setMetadataToUpdate] = useState([]);
  const [isInvalidTokenNumber, setIsInvalidTokenNumber] = useState(false);

  const handleTokenLoaded = ({
    tokenId: tokenIdToSet,
    contractAddress: contractAddressToSet,
    isInvalidTokenNumber: isInvalidTokenNumberToSet
  }) => {
    if (tokenIdToSet && tokenId !== tokenIdToSet) {
      setTokenId(tokenIdToSet);
      clearSelections();
    }
    if (contractAddress !== contractAddressToSet) {
      setContractAddress(contractAddressToSet);
    }
    if (isInvalidTokenNumberToSet !== isInvalidTokenNumber) {
      setIsInvalidTokenNumber(isInvalidTokenNumberToSet);
    }
  }

  const handleDataSelected = (data) => {
    if (metadataToUpdate.length < numberOfEntriesToSet) {
      setMetadataToUpdate([...metadataToUpdate, data]);
    }
  }

  const handleClearSelections = () => {
    clearSelections();
  }

  const clearSelections = () => setMetadataToUpdate([]);

  const handleKeepMetadataValue = () => {
    if (metadataToUpdate.length === numberOfEntriesToSet - 1 && areAllEntriesNull(metadataToUpdate)) {
      clearSelections();
      return;
    }
    setMetadataToUpdate([...metadataToUpdate, null]);
  }

  const areAllEntriesNull = (array) => array.every(val => val === null)

  const getShouldShowSendTxOrConnectToMetamask = () =>
      metadataToUpdate.length === numberOfEntriesToSet && !!contractAddress;

  return (
      <>
        <ViewToken
            tokenLoadedCallback={handleTokenLoaded}
            metadataToUpdate={metadataToUpdate}
            getMetadataToUpdateTokenUrl={getMetadataUpdatedTokenUrl}
        />
        {tokenId !== '' && !isInvalidTokenNumber && (
            <>
              <Row>
                <Col xs={2} sm={2} md={2} lg={2} xl={2} />
                <Col xs={8} sm={8} md={8} lg={8} xl={8} className="text-center" >
                  {metadataToUpdate.length > 0 && metadataToUpdate.map(
                      metadataMapper
                  )}
                </Col>
                <Col xs={2} sm={2} md={2} lg={2} xl={2} />
              </Row>
              {metadataToUpdate.length < numberOfEntriesToSet && (
                  <SelectorSection
                      metadataToUpdate={metadataToUpdate}
                      handleKeepMetadataValue={handleKeepMetadataValue}
                      handleDataSelected={handleDataSelected}
                  />
              )}
              <Row>
                <Col xs={2} sm={2} md={2} lg={2} xl={2} />
                <Col xs={8} sm={8} md={8} lg={8} xl={8} className="text-center" >
                  {metadataToUpdate.length > 0 &&
                    <Button onClick={handleClearSelections} className="styledButton text-nowrap" >
                      <p>Clear Selected</p>
                    </Button>
                  }
                </Col>
                <Col xs={2} sm={2} md={2} lg={2} xl={2} />
              </Row>
              <Row>
                <Col xs={2} sm={2} md={2} lg={2} xl={2} />
                <Col xs={8} sm={8} md={8} lg={8} xl={8} className="text-center" >
                  {getShouldShowSendTxOrConnectToMetamask() && (
                      <>
                        <PageSubHeader>Send Transaction</PageSubHeader>
                        <MetadataSetTxWrapper
                            tokenId={tokenId}
                            metadataToUpdate={metadataToUpdate}
                            contractAddress={contractAddress}
                            metadataToSetIndex={dataToSetIndex}
                            dataToSetGetter={dataToSetGetter}
                            loadDataToUpdateRelatedData={loadDataToUpdateRelatedData}
                            successCallback={successCallback}
                            attributesRegex={attributesRegex}
                            numberOfEntriesToSet={numberOfEntriesToSet}
                            account={account}
                        />
                      </>
                  )}
                </Col>
                <Col xs={2} sm={2} md={2} lg={2} xl={2} />
              </Row>
            </>
        )}
      </>
  );
}

export default UpdateTileNft;
