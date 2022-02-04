import {Button} from "react-bootstrap";
import Picker from 'emoji-picker-react';
import StyledText from "../../styledComponents/StyledText";
import styled from "styled-components";

const ColorSelectorSection = ({metadataToUpdate, handleKeepMetadataValue, handleDataSelected}) => {

  const handleEmojiPickerClick = ({target: {currentSrc}}) => {
    if (currentSrc) {
      console.log(currentSrc);
      let myRegexp = new RegExp("\/([a-zA-Z0-9]+).png");
      let match = myRegexp.exec(currentSrc);
      let emojiUnicodeValue = match[1];
      console.log("Emoji selected. Unicode: ", emojiUnicodeValue);
      if (emojiUnicodeValue) {
        handleDataSelected(emojiUnicodeValue);
      }
    }
  }

  return (
      <>
        <StyledText>Select Tile {metadataToUpdate.length + 1} Color</StyledText>
        <Button onClick={handleKeepMetadataValue} className="styledButton" >
          <p>Keep Tile {metadataToUpdate.length + 1} Emoji</p>
        </Button>
        <StyledDiv>
          <Picker onEmojiClick={handleEmojiPickerClick} />
        </StyledDiv>
      </>
  );
}

const StyledDiv =
    styled.div`
    margin-bottom: 10px;
    `;

export default ColorSelectorSection;
