import {Button} from "react-bootstrap";
import ColorSelector from "../ColorSelector";
import StyledText from "../../styledComponents/StyledText";

const ColorSelectorSection = ({metadataToUpdate, handleKeepMetadataValue, handleDataSelected}) => {
  return (
      <>
        <StyledText>Select Tile {metadataToUpdate.length + 1} Color</StyledText>
        <Button onClick={handleKeepMetadataValue} className="styledButton" >
          <p>Keep Tile {metadataToUpdate.length + 1} Color</p>
        </Button>
        <ColorSelector onAccept={handleDataSelected} />
      </>
  );
}

export default ColorSelectorSection;
