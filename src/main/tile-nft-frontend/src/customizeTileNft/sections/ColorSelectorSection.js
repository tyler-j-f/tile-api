import {Button} from "react-bootstrap";
import ColorSelector from "../ColorSelector";
import StyledText from "../../styledComponents/StyledText";
import styled from "styled-components";

const ColorSelectorSection = ({metadataToUpdate, handleKeepMetadataValue, handleDataSelected}) => {
  return (
      <>
        <StyledText>Select Tile {metadataToUpdate.length + 1} Color</StyledText>
        <Button onClick={handleKeepMetadataValue} className="styledButton" >
          <p>Keep Tile {metadataToUpdate.length + 1} Color</p>
        </Button>
        <StyledDiv>
          <ColorSelector onAccept={handleDataSelected} />
        </StyledDiv>
      </>
  );
}

const StyledDiv =
    styled.div`
    margin-bottom: 10px;
    `;

export default ColorSelectorSection;
