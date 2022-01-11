import ConnectButton from "./ConnectButton";
import MetadataSetContractWrapper from "./MetadataSetContractWrapper";
import {useEthers} from "@usedapp/core";
import ViewToken from "../view/ViewToken";
import {useState} from "react";
import {Button} from "react-bootstrap";

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
    setMetadataToUpdate([]);
  }

  const handleKeepMetadataValue = () => {
    if (metadataToUpdate.length === numberOfEntriesToSet - 1 && areAllEntriesNull(metadataToUpdate)) {
      setMetadataToUpdate([]);
      return;
    }
    setMetadataToUpdate([...metadataToUpdate, null]);
  }

  const areAllEntriesNull = (array) => array.every(val => val === null)

  return (
      <>
        <ViewToken
            tokenLoadedCallback={handleTokenLoaded}
            metadataToUpdate={metadataToUpdate}
            getMetadataToUpdateTokenUrl={getMetadataUpdatedTokenUrl}
        />
        {tokenId !== '' && !isInvalidTokenNumber && (
            <>
              {metadataToUpdate.length > 0 && metadataToUpdate.map(
                  metadataMapper
              )}
              {metadataToUpdate.length < numberOfEntriesToSet &&
                <SelectorSection
                    metadataToUpdate={metadataToUpdate}
                    handleKeepMetadataValue={handleKeepMetadataValue}
                    handleDataSelected={handleDataSelected}
                />
              }
              {metadataToUpdate.length > 0 &&
                <Button onClick={handleClearSelections}>
                  <p>Clear Selected</p>
                </Button>
              }
              <ConnectButton />
              {account && metadataToUpdate.length === numberOfEntriesToSet && contractAddress && (
                  <MetadataSetContractWrapper
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
              )}
            </>
        )}
      </>
  );
}

export default UpdateTileNft;
