import ConnectButton from "./ConnectButton";
import MetadataSetContractWrapper from "./MetadataSetContractWrapper";
import {useEthers} from "@usedapp/core";
import ViewToken from "../view/ViewToken";
import {useState} from "react";
import ColorSelector from "./ColorSelector";
import {Button} from "react-bootstrap";
import styled from "styled-components";

const NUMBER_OF_DATA_ENTRIES_TO_SET = 4;
const noop = () => {};

const UpdateTileNft = ({successCallback = noop, dataToSetIndex = null}) => {
  const {account} = useEthers();
  const [tokenId, setTokenId] = useState('');
  const [metadataToUpdate, setMetadataToUpdate] = useState([]);

  const handleTokenLoaded = (tokenIdToSet) => {
    if (tokenIdToSet && tokenId !== tokenIdToSet) {
      setTokenId(tokenIdToSet);
    }
  }

  const handleDataSelected = (data) => {
    if (metadataToUpdate.length < NUMBER_OF_DATA_ENTRIES_TO_SET) {
      setMetadataToUpdate([...metadataToUpdate, data]);
    }
  }

  const handleClearSelections = () => {
    setMetadataToUpdate([]);
  }

  const handleKeepMetadataValue = () => {
    if (metadataToUpdate.length === NUMBER_OF_DATA_ENTRIES_TO_SET - 1 && areAllEntriesNull(metadataToUpdate)) {
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
        />
        {tokenId !== '' && (
            <>
              {metadataToUpdate.length > 0 && metadataToUpdate.map(
                  (metaData, index) => metaData === null ? null :<StyledText>Tile {index + 1} updated color value: {metaData.hex}</StyledText>
              )}
              {metadataToUpdate.length < NUMBER_OF_DATA_ENTRIES_TO_SET &&
                  <>
                    <StyledText>Select Tile {metadataToUpdate.length + 1} Color</StyledText>
                    <Button onClick={handleKeepMetadataValue}>
                      <p>Keep Tile {metadataToUpdate.length + 1} Color</p>
                    </Button>
                    <ColorSelector onAccept={handleDataSelected} />
                  </>
              }
              {metadataToUpdate.length > 0 &&
                <Button onClick={handleClearSelections}>
                  <p>Clear Selected</p>
                </Button>
              }
              <ConnectButton />
              {account && metadataToUpdate.length === NUMBER_OF_DATA_ENTRIES_TO_SET && (
                  <MetadataSetContractWrapper
                      tokenId={tokenId}
                      metadataToUpdate={metadataToUpdate}
                      contractAddress={CONTRACT_ADDRESS}
                      metadataToSetIndex={dataToSetIndex}
                      successCallback={successCallback}
                  />
              )}
            </>
        )}
      </>
  );
}

const StyledText =
    styled.p`
    color: white;
    font-weight: bold;
    `;


export const DATA_TO_SET_INDEX = 0;
export const DATA_TO_SET = "0x0000111000022200003330000444000000000000000000000000000000000000";
export const CONTRACT_ADDRESS = "0xEc9547ABc4a8c24B99226BeE239c6E29814903Cd";

export default UpdateTileNft;
