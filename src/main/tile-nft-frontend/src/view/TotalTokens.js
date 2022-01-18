import StyledText from "../styledComponents/StyledText";
import {useEffect} from "react";
import loadTotalTokensData from "./loadTotalTokensData";

const TotalTokens = ({
  totalUnburntTokens,
  totalTokens,
  useLoadData = false
}) => {

  useEffect(
      (useLoadData) => {
        loadTotalTokensData().then(r => console.log("TotalTokens useLoadData", r));
      },
      []
  );

  return (
      <ul>
        <li>
          <StyledText className="centered" >
            {totalUnburntTokens} total unburnt tokens.
          </StyledText>
        </li>
        <li>
          <StyledText className="centered" >
            {totalTokens} total tokens, including burnt tokens.
          </StyledText>
        </li>
      </ul>
  );
}

export default TotalTokens;
