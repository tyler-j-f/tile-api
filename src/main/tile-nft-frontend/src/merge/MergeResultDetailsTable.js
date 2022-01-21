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

  const getRowOne = () => {
    return (
        <tr>
          <td><StyledText>Merged TileNFT, Tile # Rarity (M<sub>R#</sub>)</StyledText></td>
          <td><StyledText>(B1<sub>R#</sub> * B1<sub>M#</sub> * B2<sub>MM</sub>) + (B2<sub>R#</sub> * B2<sub>M#</sub> * B1<sub>MM</sub>)</StyledText></td>
        </tr>
    );
  }

  const getTableRows = () => {
    let row1 = getRowOne();
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
