const loadNumberOfTokens = ({
  leaderboardData
}) => {
  return fetch(
      `${window.location.origin}/api/leaderboard/getNumberOfUnburntTokens`,
      {method: 'get'}
  )
  .then(response => {
    console.log(response);
    return response.json();
  })
  .then(numberOfTokens => {
    let maxPaginationPage = Math.ceil(numberOfTokens/leaderboardData.itemsPerPage);
    return {
      ...leaderboardData,
      maxPaginationPage: maxPaginationPage,
      isLoading: false,
      isGeneralError: false
    };
  })
  .catch(err => {
    console.log("loadNumberOfTokens error caught!!!", err);
    return {
      ...leaderboardData,
      isLoading: false,
      isGeneralError: true
    };
  });
}

export default loadNumberOfTokens;
