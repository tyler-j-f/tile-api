import {useEffect, useState} from 'react';
import styled from 'styled-components';
import StyledText from "../styledComponents/StyledText";
import TotalTokens from "./TotalTokens";

const OverallRank = ({tokenId = ''}) => {

  const [overallRankData, setOverallRankData] = useState({
    rank: null,
    totalTokenRanks: null,
    totalUnburntTokens: null,
    totalTokens: null
  });

  useEffect(() => {
    if (tokenId !== '') {
      loadOverallRank().then((response) => {
        if (response === null) {
          return;
        }
        setOverallRankData({
          rank: response.rank,
          totalTokenRanks: response.totalTokenRanks,
          totalUnburntTokens: response.totalUnburntTokens,
          totalTokens: response.totalTokens,
        })}).catch(err => {
        console.log("Error caught!!!", err);
      });
    }
  }, [tokenId]);

  const loadOverallRank = () => {
    return fetch(
        `${window.location.origin}/api/leaderboard/getOverallRank/${tokenId}`,
        {method: 'get'}
    )
    .then(response => {
      if (response.status === 200) {
        return response.json();
      }
      return null;
    })
    .then(json => {
      console.log("OverallRank loadOverallRank json found", json);
      if (json === null) {
        let errorMessage = 'loadOverallRank json is null';
        console.log(errorMessage);
        throw errorMessage;
      }
      return json
    });
  }

  const getShouldRender = () => {
    return overallRankData.rank !== null && overallRankData.totalTokenRanks !== null && overallRankData.totalUnburntTokens !== null && overallRankData.totalTokens !== null;
  }

  return getShouldRender() && (
      <>
        <StyledText className="centered" >
          Overall Rank: {overallRankData.rank} / {overallRankData.totalTokenRanks}
        </StyledText>
        <TotalTokens
            totalUnburntTokens={overallRankData.totalUnburntTokens}
            totalTokens={overallRankData.totalTokens}
        />
      </>
  );
}

export default OverallRank;
