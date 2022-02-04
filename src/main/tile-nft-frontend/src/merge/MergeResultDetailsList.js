import StyledText from "../styledComponents/StyledText";

const MergeResultDetailsList = ({}) => {
  return (
    <ul>
      <li>
        <StyledText>B1&nbsp;&nbsp;&nbsp;=&nbsp;Burned TileNFT 1</StyledText>
      </li>
      <li>
        <StyledText>B2&nbsp;&nbsp;&nbsp;=&nbsp;Burned TileNFT 2</StyledText>
      </li>
      <li>
        <StyledText>B1<sub>R#</sub>&nbsp;=&nbsp;Burned TileNFT 1, Tile #, Rarity Value</StyledText>
      </li>
      <li>
        <StyledText>B1<sub>M#</sub>&nbsp;=&nbsp;Burned TileNFT 1, Tile #, Multiplier Value</StyledText>
      </li>
      <li>
        <StyledText>B1<sub>MM</sub>&nbsp;=&nbsp;Burned TileNFT 1, Merge Multiplier Value</StyledText>
      </li>
      <li>
        <StyledText>M&nbsp;&nbsp;&nbsp;&nbsp;=&nbsp;Merged TileNFT (Created from merging B1 and B2)</StyledText>
      </li>
      <li>
        <StyledText>M<sub>R#</sub>&nbsp;=&nbsp;Merged TileNFT, Tile #, Rarity Value</StyledText>
      </li>
      <li>
        <StyledText>M<sub>M#</sub>&nbsp;=&nbsp;Merged TileNFT, Tile #, Multiplier Value</StyledText>
      </li>
      <li>
        <StyledText>M<sub>MM</sub>&nbsp;=&nbsp;Merged TileNFT, Merge Multiplier Value</StyledText>
      </li>
    </ul>
  );
}

export default MergeResultDetailsList;
