import {Button} from "react-bootstrap";
import styled from "styled-components";
import Picker from 'emoji-picker-react';

const ColorSelectorSection = ({metadataToUpdate, handleKeepMetadataValue, handleDataSelected}) => {

  const handleEmojiPickerClick = ({target: {currentSrc}}) => {
    if (currentSrc) {
      console.log(currentSrc);
      let myRegexp = new RegExp("\/([a-zA-Z0-9]+.png)");
      let match = myRegexp.exec(currentSrc);
      console.log(match);
      let toMatch2 = 'https://cdn.jsdelivr.net/npm/emoji-datasource-apple@6.0.1/img/apple/64/1f3c2.png';
      let match2 = myRegexp.exec(toMatch2);
      console.log(match2);
    }
  }

  return (
      <>
        <StyledText>Select Tile {metadataToUpdate.length + 1} Color</StyledText>
        <Button onClick={handleKeepMetadataValue}>
          <p>Keep Tile {metadataToUpdate.length + 1} Color</p>
        </Button>
        <Picker onEmojiClick={handleEmojiPickerClick} />
      </>
  );
}

const StyledText =
    styled.p`
    color: white;
    font-weight: bold;
    `;

export default ColorSelectorSection;
