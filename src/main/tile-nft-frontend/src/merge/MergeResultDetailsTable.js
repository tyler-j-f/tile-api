import {Table} from "react-bootstrap";
import StyledText from "../styledComponents/StyledText";
import React from "react";

const MergeResultDetailsTable = ({}) => {

  const getTableHeader = () => {
    return (
        <thead>
        <tr>
          <th scope="col"><StyledText>Attribute</StyledText></th>
          <th scope="col"><StyledText>Value</StyledText></th>
        </tr>
        </thead>
    );
  }

  const getAttributeRow = ({traitType, valueDescription}) => (
      <tr>
        <td><StyledText>{traitType}</StyledText></td>
        <td><StyledText>{valueDescription}</StyledText></td>
      </tr>
  );

  const getTableRows = () => {
    let row1 = getAttributeRow({
      traitType: "Merged TileNFT, Tile # Rarity (MR#)",
      valueDescription: "(B1R# * B1M# * B2MM) + (B2R# * B2M# * B1MM)"
    });
    let row2 = getAttributeRow({
      traitType: "Merged TileNFT, Tile # Multiplier (MM#)",
      valueDescription: "1"
    });
    let row3 = getAttributeRow({
      traitType: "Merged TileNFT, Tile # Rarity (MR#)",
      valueDescription: "Random value:  0 < x <= 10"
    });
    return (
        <tbody>
          {row1}
          {row2}
          {row3}
        </tbody>
    );
  }

  return (
      <Table className="table">
        {getTableHeader()}
        {getTableRows()}
      </Table>
  );
}

export default MergeResultDetailsTable;
