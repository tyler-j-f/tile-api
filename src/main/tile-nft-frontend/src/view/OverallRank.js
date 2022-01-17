import {useEffect, useState} from 'react';
import styled from 'styled-components';

const OverallRank = ({tokenId = ''}) => {

  const [overallRankData, setOverallRankData] = useState({
    rank: null,
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
          totalTokens: response.totalTokens
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

  return (
      <>
        {overallRankData.rank !== null && overallRankData.totalTokens !== null && (
            <StyledText>
              Overall Rank: {overallRankData.rank} / {overallRankData.totalTokens}
            </StyledText>
        )}
      </>
  );

}

const StyledText =
    styled.p`
    color: white;
    font-weight: bold;
    font-size: 20px;
    `;

export default OverallRank;
