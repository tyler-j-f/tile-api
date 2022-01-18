import React, {useState} from 'react'
import UpdateTileNft from "../updateTileNft/UpdateTileNft";
import {Button} from "react-bootstrap";
import ColorSelectorSection from "../updateTileNft/sections/ColorSelectorSection";
import colorDataToSetGetter
  from "../updateTileNft/dataToSetGetters/colorDataToSetGetter";
import emojiDataToSetGetter
  from "../updateTileNft/dataToSetGetters/emojiDataToSetGetter";
import EmojiSelectorSection
  from "../updateTileNft/sections/EmojiSelectorSection";
import getColorsMetadataUpdatedTokenUrl
  from "../view/metadataUpdatedTokenUrlGetters/getColorsMetadataUpdatedTokenUrl";
import getEmojisMetadataUpdatedTokenUrl
  from "../view/metadataUpdatedTokenUrlGetters/getEmojisMetadataUpdatedTokenUrl";
import loadColorTokenAttributes
  from "../updateTileNft/tokenDataLoaders/loadColorTokenAttributes";
import loadEmojiTokenAttributes
  from "../updateTileNft/tokenDataLoaders/loadEmojiTokenAttributes";
import TransactionSuccess from "../etc/TransactionSuccess";
import StyledPage from "../styledComponents/StyledPage";
import PageHeader from "../styledComponents/PageHeader";
import StyledText from "../styledComponents/StyledText";

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
            getMetadataUpdatedTokenUrl={getColorsMetadataUpdatedTokenUrl}
            loadDataToUpdateRelatedData={loadColorTokenAttributes}
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
            getMetadataUpdatedTokenUrl={getEmojisMetadataUpdatedTokenUrl}
            loadDataToUpdateRelatedData={loadEmojiTokenAttributes}
        />
    );
  }

  const getSelectWhatToUpdateButtons = () => {
    return (
        <>
          {txData.dataToSetIndex !== null && <Button onClick={() => handleSelectWhatToUpdateClicked(null)} className="styledButton" >Back</Button>}
          {txData.dataToSetIndex === null && (
              <>
                <Button onClick={() => handleSelectWhatToUpdateClicked(0)} className="styledButton" >Update Colors</Button>
                <Button onClick={() => handleSelectWhatToUpdateClicked(1)} className="styledButton" >Update Emojis</Button>
              </>
          )}
        </>
    );
  }

  return (
      <StyledPage>
        <PageHeader>Update TileNFT</PageHeader>
        {!txData.isSuccess && getSelectWhatToUpdateButtons()}
        {txData.dataToSetIndex !== null && getUpdateHtml()}
        {txData.isSuccess &&
          <TransactionSuccess
              handleSendAnotherTx={handleSendAnotherTx}
              txId={txData.txId}
              subText={'Please wait a few minutes for the transaction to process and the TileNFT to be updated.'}
          />
        }
      </StyledPage>
  )
}

export default UpdateTileNftPage
