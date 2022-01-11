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

  const getAttributeRow = (attribute, index) => {
    return (
        <tr>
          <th scope="row"><StyledText>{index + 1}</StyledText></th>
          <td><StyledText>{attribute.trait_type}</StyledText></td>
          <td><StyledText>{attribute.value}</StyledText></td>
        </tr>
    );
  }

  const getAttributesHtmlRows = () => {
    let overallRarityTraitType = 'Overall Rarity';
    let emojiSourceTraitType = 'Emoji Source';
    let attributesArray = Object.values(tokenAttributes);
    let attributeNumber = 0;
    let overallRarityRow =
        attributesArray.map(
            (attribute) => {
              if (attribute.trait_type === overallRarityTraitType) {
                return getAttributeRow(attribute, attributeNumber++);
              }
            }
        );
    let rows =
       attributesArray.map(
            (attribute) => {
              if (attribute.trait_type !== overallRarityTraitType && attribute.trait_type !== emojiSourceTraitType) {
                return getAttributeRow(attribute, attributeNumber++);
              }
            }
        );
    let emojiSourceRow =
        attributesArray.map(
            (attribute) => {
              if (attribute.trait_type === emojiSourceTraitType) {
                return getAttributeRow(attribute, attributeNumber++);
              }
            }
        );
    return (
        <tbody>
          {overallRarityRow}
          {rows}
          {emojiSourceRow}
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
