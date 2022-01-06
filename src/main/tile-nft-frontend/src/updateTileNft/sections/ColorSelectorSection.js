import {Button} from "react-bootstrap";
import ColorSelector from "../ColorSelector";
import styled from "styled-components";

const ColorSelectorSection = ({metadataToUpdate, handleKeepMetadataValue, handleDataSelected}) => {
  return (
      <>
        <StyledText>Select Tile {metadataToUpdate.length + 1} Color</StyledText>
        <Button onClick={handleKeepMetadataValue}>
          <p>Keep Tile {metadataToUpdate.length + 1} Color</p>
        </Button>
        <ColorSelector onAccept={handleDataSelected} />
      </>
  );
}

const StyledText =
    styled.p`
    color: white;
    font-weight: bold;
    `;

export default ColorSelectorSection;
