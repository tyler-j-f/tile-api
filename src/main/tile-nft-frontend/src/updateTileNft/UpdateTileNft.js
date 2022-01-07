import ConnectButton from "./ConnectButton";
import MetadataSetContractWrapper from "./MetadataSetContractWrapper";
import {useEthers} from "@usedapp/core";
import ViewToken from "../view/ViewToken";
import {useState} from "react";
import {Button} from "react-bootstrap";

const noop = () => {};

const UpdateTileNft = ({
  contractAddress = "0xEc9547ABc4a8c24B99226BeE239c6E29814903Cd",
  successCallback = noop,
  metadataMapper = noop,
  dataToSetGetter = noop,
  getMetadataUpdatedTokenUrl = noop,
  dataToSetIndex = null,
  SelectorSection = null,
  attributesRegex = '',
  numberOfEntriesToSet = 4
}) => {
  const {account} = useEthers();
  const [tokenId, setTokenId] = useState('');
  const [metadataToUpdate, setMetadataToUpdate] = useState([]);

  const handleTokenLoaded = (tokenIdToSet) => {
    if (tokenIdToSet && tokenId !== tokenIdToSet) {
      setTokenId(tokenIdToSet);
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
            getMetadataUpdatedTokenUrl={getMetadataUpdatedTokenUrl}
        />
        {tokenId !== '' && (
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
              {account && metadataToUpdate.length === numberOfEntriesToSet && (
                  <MetadataSetContractWrapper
                      tokenId={tokenId}
                      metadataToUpdate={metadataToUpdate}
                      contractAddress={contractAddress}
                      metadataToSetIndex={dataToSetIndex}
                      dataToSetGetter={dataToSetGetter}
                      successCallback={successCallback}
                      attributesRegex={attributesRegex}
                      numberOfEntriesToSet={numberOfEntriesToSet}
                  />
              )}
            </>
        )}
      </>
  );
}

export default UpdateTileNft;
