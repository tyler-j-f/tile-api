import React, {useState} from 'react'
import CustomizeTileNft from "../customizeTileNft/CustomizeTileNft";
import {Button} from "react-bootstrap";
import ColorSelectorSection from "../customizeTileNft/sections/ColorSelectorSection";
import colorDataToSetGetter
  from "../customizeTileNft/dataToSetGetters/colorDataToSetGetter";
import emojiDataToSetGetter
  from "../customizeTileNft/dataToSetGetters/emojiDataToSetGetter";
import EmojiSelectorSection
  from "../customizeTileNft/sections/EmojiSelectorSection";
import getColorsMetadataUpdatedTokenUrl
  from "../view/metadataUpdatedTokenUrlGetters/getColorsMetadataUpdatedTokenUrl";
import getEmojisMetadataUpdatedTokenUrl
  from "../view/metadataUpdatedTokenUrlGetters/getEmojisMetadataUpdatedTokenUrl";
import loadColorTokenAttributes
  from "../customizeTileNft/tokenDataLoaders/loadColorTokenAttributes";
import loadEmojiTokenAttributes
  from "../customizeTileNft/tokenDataLoaders/loadEmojiTokenAttributes";
import TransactionSuccess from "../etc/TransactionSuccess";
import StyledPage from "../styledComponents/StyledPage";
import PageHeader from "../styledComponents/PageHeader";
import StyledText from "../styledComponents/StyledText";

const CuztomizeTileNftPage = () => {
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
        <CustomizeTileNft
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
        <CustomizeTileNft
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
          {txData.dataToSetIndex !== null && <Button onClick={() => handleSelectWhatToUpdateClicked(null)} className="styledButton" ><p>Back</p></Button>}
          {txData.dataToSetIndex === null && (
              <>
                <Button onClick={() => handleSelectWhatToUpdateClicked(0)} className="styledButton" ><p>Update Colors</p></Button>
                <Button onClick={() => handleSelectWhatToUpdateClicked(1)} className="styledButton" ><p>Update Emojis</p></Button>
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

export default CuztomizeTileNftPage
