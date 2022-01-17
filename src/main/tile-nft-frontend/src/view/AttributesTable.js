import React from "react";
import styled from "styled-components";
import StyledText from "../styledComponents/StyledText";

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

  const getPointsSuffix = ({attribute}) => {
    return (attribute?.value && attribute.value > 1)  ? "points" : "point";
  }

  const getAttributePointsRow = (attribute, index) => {
    let formatIntegerRegex = /\B(?=(\d{3})+(?!\d))/g;
    return (
        <tr>
          <th scope="row"><StyledText>{index + 1}</StyledText></th>
          <td><StyledText>{attribute.trait_type}</StyledText></td>
          <td><StyledText>{attribute.value.toString().replace(formatIntegerRegex, ",")} {getPointsSuffix({attribute})}</StyledText></td>
        </tr>
    );
  }

  const getAttributeRow = (attribute, index) => (
      <tr>
        <th scope="row"><StyledText>{index + 1}</StyledText></th>
        <td><StyledText>{attribute.trait_type}</StyledText></td>
        <td><StyledText>{attribute.value}</StyledText></td>
      </tr>
  );

  const getAttributesHtmlRows = () => {
    let overallRarityTraitType = 'Overall Rarity';
    let emojiSourceTraitType = 'Emoji Source';
    let mergeMultiplierTraitType = 'Merge Multiplier';
    let rarityAttributesRegex = /Tile \d Rarity/;
    let multiplierAttributesRegex = /Tile \d Multiplier/;
    let attributesArray = Object.values(tokenAttributes);
    let attributeNumber = 0;
    let overallRarityRow =
        attributesArray.map(
            (attribute) => {
              if (attribute.trait_type === overallRarityTraitType) {
                return getAttributePointsRow(attribute, attributeNumber++);
              }
            }
        );
    let tileRarityRows =
        attributesArray.map(
            (attribute) => {
              if (attribute.trait_type.match(rarityAttributesRegex)) {
                return getAttributePointsRow(attribute, attributeNumber++);
              }
            }
        );
    let tileMultiplierRows =
        attributesArray.map(
            (attribute) => {
              if (attribute.trait_type.match(multiplierAttributesRegex)) {
                return getAttributeRow(attribute, attributeNumber++);
              }
            }
        );
    let mergeMultiplierRow =
        attributesArray.map(
            (attribute) => {
              if (attribute.trait_type === mergeMultiplierTraitType) {
                return getAttributeRow(attribute, attributeNumber++);
              }
            }
        );
    let otherAttributesRows =
       attributesArray.map(
            (attribute) => {
              if (
                  attribute.trait_type !== overallRarityTraitType &&
                  attribute.trait_type !== emojiSourceTraitType &&
                  !attribute.trait_type.match(rarityAttributesRegex) &&
                  !attribute.trait_type.match(multiplierAttributesRegex) &&
                  !attribute.trait_type.match(mergeMultiplierTraitType)
              ) {
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
          {tileRarityRows}
          {tileMultiplierRows}
          {mergeMultiplierRow}
          {otherAttributesRows}
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

export default AttributesTable;
