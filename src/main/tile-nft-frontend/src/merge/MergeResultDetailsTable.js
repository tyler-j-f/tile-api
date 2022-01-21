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

  const getRowTwo = () => {
    return (
        <tr>
          <td><StyledText>Merged TileNFT, Tile # Multiplier (M<sub>M#</sub>)</StyledText></td>
          <td><StyledText>1</StyledText></td>
        </tr>
    );
  }

  const getRowThree = () => {
    let valueDescription = "Random value:  0 < x <= 10";
    return (
        <tr>
          <td><StyledText>Merged TileNFT, Tile # Rarity (M<sub>R#</sub>)</StyledText></td>
          <td><StyledText>{valueDescription}</StyledText></td>
        </tr>
    );
  }

  const getTableRows = () => {
    let row1 = getRowOne();
    let row2 = getRowTwo();
    let row3 = getRowThree();
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
