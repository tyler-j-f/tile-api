import React from "react";
import styled from "styled-components";

const AttributesTable = ({tokenAttributes}) => {

  const getAttributesHtmlHeader = () => {
    return (
        <thead>
        <tr>
          <th scope="col"><StyledText>#</StyledText></th>
          <th scope="col"><StyledText>Trait Type</StyledText></th>
          <th scope="col"><StyledText>Value</StyledText></th>
        </tr>
        </thead>
    );
  }

  const getAttributesHtmlRows = () => {
    let rows = Object.values(tokenAttributes).map((attribute, index) => {
      return (
          <tr>
            <th scope="row"><StyledText>{index + 1}</StyledText></th>
            <td><StyledText>{attribute.trait_type}</StyledText></td>
            <td><StyledText>{attribute.value}</StyledText></td>
          </tr>
      );
    });
    return (
        <tbody>
        {rows}
        </tbody>
    );
  }

  return (
      <table className="table">
        {getAttributesHtmlHeader()}
        {getAttributesHtmlRows()}
      </table>
  )
}

const StyledText =
    styled.p`
  color: white;
  font-weight: bold;
`;

export default AttributesTable;
