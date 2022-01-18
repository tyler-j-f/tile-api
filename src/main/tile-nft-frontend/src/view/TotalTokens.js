import StyledText from "../styledComponents/StyledText";
import {useEffect, useState} from "react";
import loadTotalTokensData from "./loadTotalTokensData";

const TotalTokens = ({
  totalUnburntTokens,
  totalTokens,
  useLoadData = false
}) => {

  const [totalTokensData, setTotalTokensData] = useState({
    totalUnburntTokens,
    totalTokens
  });

  useEffect(
      () => {
        if (useLoadData) {
          loadTotalTokensData().then(result => {
            console.log("TotalTokens useLoadData", result);
            setTotalTokensData({
              ...totalTokensData,
              ...result
            });
          });
        }
      },
      []
  );

  return (
      <ul>
        <li>
          <StyledText className="centered" >
            {totalTokensData.totalUnburntTokens} total unburnt tokens.
          </StyledText>
        </li>
        <li>
          <StyledText className="centered" >
            {totalTokensData.totalTokens} total tokens, including burnt tokens.
          </StyledText>
        </li>
      </ul>
  );
}

export default TotalTokens;
