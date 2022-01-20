import React, {useEffect, useState} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';
import StyledLabel from "../styledComponents/StyledLabel";
import TotalTokens from "../view/TotalTokens";
import LeaderboardPagination from "./LeaderboardPagination";
import loadNumberOfTokens from "./loadNumberOfTokens";
import loadLeaders from "./loadLeaders";

const Leaderboard = () => {

  const [leaderboardData, setLeaderboardData] = useState({
    isGeneralError: false,
    isLoading: false,
    leaders: [],
    itemsPerPage: 10,
    paginationPage: 1,
    maxPaginationPage: 10,
    totalUnburntTokens: null,
    totalTokens: null
  });

  useEffect(
      () => {
        loadLeaderboardData(1);
      },
      []
  );

  const getTotalTokens = () => {
    return (
        <TotalTokens
            totalUnburntTokens={leaderboardData.totalUnburntTokens}
            totalTokens={leaderboardData.totalTokens}
            useLoadData
        />
    )
  }

  const loadLeaderboardData = (pageNumber) => {
    setLeaderboardData({
      ...leaderboardData,
      isLoading: true
    });
    return loadNumberOfTokens({
      leaderboardData
    }).then(
        loadNumberOfTokensResult =>
            loadLeaders({
              leaderboardData,
              setLeaderboardData,
              pageNumber,
              loadNumberOfTokensResult
            })
    );
  }

  const getLeaderboard = () => {
    if (leaderboardData.isGeneralError) {
      return getGeneralErrorText();
    }
    let leaderboard =  leaderboardData.leaders.map(
        (
            {tokenId, rankCount}
        ) => getTokenImage(tokenId, rankCount)
    );
    let totalTokens = getTotalTokens();
    return (
        <>
          {totalTokens}
          {leaderboard}
          <LeaderboardPagination
            leaderboardData={leaderboardData}
            setLeaderboardData={setLeaderboardData}
            loadLeaderboardData={loadLeaderboardData}
          />
        </>
    );
  }

  const getGeneralErrorText = () => {
    return (
        <StyledErrorText>Error! Please try again later.</StyledErrorText>
    )
  }

  const getTokenImage = (tokenId, rank) => {
    return (
        <div>
          <StyledLabel># {rank}</StyledLabel>
          <StyledImg imgSource={`${window.location.origin}/api/image/tile/get/${tokenId}`} />
        </div>
    );
  }

  const getSpinner = () => {
    return (
        <Spinner animation="border" variant="primary" />
    );
  }

  let isLoading = leaderboardData.isLoading && leaderboardData.leaders !== [];
  let loadingSymbol = isLoading ? getSpinner() : null;
  let leaderboard = isLoading ? null : getLeaderboard();
  return isLoading ? loadingSymbol : leaderboard;
}

const StyledImg =
    styled.img.attrs(props => ({
      src: props.imgSource
    }))`
    @media screen and (min-width: 501px) {
      width: 350px;
      height: 350px;
    }
    @media screen and (max-width: 500px) and (min-width: 321px) {
      width: 200px;
      height: 200px;
    }
    @media screen and (max-width: 320px) {
      width: 150px;
      height: 150px;
    }
    margin: 10px;
    display: block;
    `;

const StyledErrorText =
    styled.p`
    display: block;
    color: #FF4500;
    text-align: center;
    `;

export default Leaderboard;
