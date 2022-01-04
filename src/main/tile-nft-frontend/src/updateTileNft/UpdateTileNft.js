import ConnectButton from "./ConnectButton";
import MetadataSetContractWrapper from "./MetadataSetContractWrapper";
import {useEthers} from "@usedapp/core";
import ViewToken from "../view/ViewToken";
import {useState} from "react";
import ColorSelector from "./ColorSelector";
import {Button} from "react-bootstrap";
import styled from "styled-components";

const NUMBER_OF_COLORS_TO_SET = 4;

const UpdateTileNft = () => {
  const {account} = useEthers();
  const [tokenId, setTokenId] = useState('');
  const [colorsToUpdate, setColorsToUpdate] = useState([]);

  const handleTokenLoaded = (tokenId) => {
    if (tokenId) {
      setTokenId(tokenId);
      console.log("Token id found in callback", tokenId)
    }
  }

  const handleColorSelected = (color) => {
    console.log('handleColorSelected', color);
    if (colorsToUpdate.length < NUMBER_OF_COLORS_TO_SET) {
      setColorsToUpdate([...colorsToUpdate, color]);
    }
  }

  const handleClearSelectedColors = () => {
    console.log('handleClearSelectedColors');
    setColorsToUpdate([]);
  }

  const handleClickKeepTileNumberColor = () => {
    console.log('handleClickKeepTileNumberColor');
    setColorsToUpdate([...colorsToUpdate, null]);
  }

  return (
      <>
        <ViewToken
            tokenLoadedCallback={handleTokenLoaded}
            colorsToUpdate={colorsToUpdate}
        />
        {tokenId !== '' && (
            <>
              {colorsToUpdate.length > 0 && colorsToUpdate.map(
                  (colorHex, index) => handleColorSelected === null ? null :<StyledText>Tile {index + 1} color value: {colorHex}</StyledText>
              )}
              {colorsToUpdate.length < NUMBER_OF_COLORS_TO_SET &&
                  <>
                    <StyledText>Select Tile {colorsToUpdate.length + 1} Color</StyledText>
                    <Button onClick={handleClickKeepTileNumberColor}>
                      <p>Keep Tile ${colorsToUpdate.length + 1} Color</p>
                    </Button>
                    <ColorSelector onAccept={handleColorSelected} />
                  </>
              }
              {colorsToUpdate.length > 0 &&
                <Button onClick={handleClearSelectedColors}>
                  <p>Clear Selected Colors</p>
                </Button>
              }
              <ConnectButton />
              {account && colorsToUpdate.length === NUMBER_OF_COLORS_TO_SET && (
                  <MetadataSetContractWrapper
                      tokenId={tokenId}
                      contractAddress={CONTRACT_ADDRESS}
                      dataToSet={DATA_TO_SET}
                      dataToSetIndex={DATA_TO_SET_INDEX}
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
export const CONTRACT_ADDRESS     = "0xEc9547ABc4a8c24B99226BeE239c6E29814903Cd";

export default UpdateTileNft;
