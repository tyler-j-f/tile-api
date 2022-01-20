const loadLeaders = ({
  leaderboardData, setLeaderboardData, pageNumber, loadNumberOfTokensResult
}) => {

  const getLeaderboardUrl = (pageNumber) => {
    let startIndex = (pageNumber - 1) * leaderboardData.itemsPerPage;
    let endIndex = startIndex + leaderboardData.itemsPerPage;
    return `${window.location.origin}/api/leaderboard/getLeaders?startIndex=${startIndex}&endIndex=${endIndex}`;
  }

  return fetch(getLeaderboardUrl(pageNumber), {method: 'get'})
  .then(response => {
    console.log(response);
    return response.json();
  })
  .then(leaders => {
    setLeaderboardData({
      ...leaderboardData,
      ...loadNumberOfTokensResult,
      leaders,
      paginationPage: pageNumber,
      isLoading: false,
      isGeneralError: false
    });
  })
  .catch(err => {
    setLeaderboardData({
      ...leaderboardData,
      ...loadNumberOfTokensResult,
      isLoading: false,
      isGeneralError: true
    });
    console.log("getLeaderboardUrl error caught!!!", err);
  });
}

export default loadLeaders;
