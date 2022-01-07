import React, {useState} from 'react'
import styled from 'styled-components';
import UpdateTileNft from "../updateTileNft/UpdateTileNft";
import {Button} from "react-bootstrap";
import ColorSelectorSection from "../updateTileNft/sections/ColorSelectorSection";
import colorDataToSetGetter
  from "../updateTileNft/dataToSetGetters/colorDataToSetGetter";
import emojiDataToSetGetter
  from "../updateTileNft/dataToSetGetters/emojiDataToSetGetter";
import EmojiSelectorSection
  from "../updateTileNft/sections/EmojiSelectorSection";

const UpdateTileNftPage = () => {
  const [txData, setTxData] = useState({
    isSuccess: false,
    txId: '',
    dataToSetIndex: null
  });

  const handleSuccessfulTx = (txId) => {
    setTxData({
      isSuccess: true,
      txId: txId,
      dataToSetIndex: null
    });
  }

  const handleSendAnotherTx = () => {
    setTxData({
      isSuccess: false,
      txId: '',
      dataToSetIndex: null
    });
  }

  const handleSelectWhatToUpdateClicked = (index) => {
    setTxData({
      ...txData,
      dataToSetIndex: index
    });
  }

  const getSuccessHtml = () => {
    return (
        <>
          <StyledText>
            Success!!! Transaction Id: <a href={`https://rinkeby.etherscan.io/tx/${txData.txId}`} >{txData.txId}</a>
          </StyledText>
          <StyledText>Please wait a few minutes for the transaction to process and the TileNft to be updated.</StyledText>
          <Button onClick={handleSendAnotherTx}>
            Send another update transaction?
          </Button>
        </>
    );
  }

  const getUpdateHtml = () => {
    return (
        <>
          {txData.dataToSetIndex === 0 && getUpdateColorsHtml()}
          {txData.dataToSetIndex === 1 && getUpdateEmojisHtml()}
        </>
    );
  }

  const getUpdateColorsHtml = () => {
    return (
        <UpdateTileNft
            successCallback={handleSuccessfulTx}
            dataToSetIndex={0}
            SelectorSection={
              ColorSelectorSection
            }
            attributesRegex={/Tile \d Color/}
            metadataMapper={
              (data, index) => data !== null ? <StyledText>Tile {index + 1} updated color value: {data.hex}</StyledText> : null
            }
            dataToSetGetter={colorDataToSetGetter}
        />
    );
  }

  const getUpdateEmojisHtml = () => {
    return (
        <UpdateTileNft
            successCallback={handleSuccessfulTx}
            dataToSetIndex={1}
            SelectorSection={
              EmojiSelectorSection
            }
            attributesRegex={/Tile \d Emoji/}
            metadataMapper={
              (data, index) => data !== null ? <StyledText>Tile {index + 1} updated emoji value: {data}</StyledText> : null
            }
            dataToSetGetter={emojiDataToSetGetter}
        />
    );
  }

  const getSelectWhatToUpdateButtons = () => {
    return (
        <>
          {txData.dataToSetIndex !== null && <Button onClick={() => handleSelectWhatToUpdateClicked(null)} >Back</Button>}
          {txData.dataToSetIndex === null && (
              <>
                <Button onClick={() => handleSelectWhatToUpdateClicked(0)} >Update Colors</Button>
                <Button onClick={() => handleSelectWhatToUpdateClicked(1)} >Update Emojis</Button>
              </>
          )}
        </>
    );
  }

  return (
      <StyledUpdateTileNftPage>
        <Heading className="animate__animated animate__fadeInLeft">Update TileNft</Heading>
        {!txData.isSuccess && getSelectWhatToUpdateButtons()}
        {txData.dataToSetIndex !== null && getUpdateHtml()}
        {txData.isSuccess && getSuccessHtml()}
      </StyledUpdateTileNftPage>
  )
}

const StyledUpdateTileNftPage = styled.div`
    min-height: 100vh;
    width: 100vw;
    background-color: #282c34;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;

const Heading = styled.h1`
    font-size: clamp(3rem, 5vw, 7vw);
    color: #eee;
    font-weight: 700;
    margin: 0;
    padding: 0;

    user-select: none; /* supported by Chrome and Opera */
   -webkit-user-select: none; /* Safari */
   -khtml-user-select: none; /* Konqueror HTML */
   -moz-user-select: none; /* Firefox */
   -ms-user-select: none; /* Internet Explorer/Edge */
`;

const StyledText =
    styled.p`
    color: white;
    font-weight: bold;
    `;

export default UpdateTileNftPage
