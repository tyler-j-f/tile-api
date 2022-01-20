const loadMergeTokensOutputtedToken = ({
  tokenId1,
  tokenId2
}) => {
  return fetch(`${window.location.origin}/api/leaderboard/getMergeOverallRarity/${tokenId1}/${tokenId2}`, {method: 'get'})
  .then(response => {
    if (response.status === 200) {
      return response.json();
    }
    return null;
  })
  .then(json => {
    if (json === null) {
      let errorMessage = 'loadMergeTokensOutputtedToken response is null';
      console.log(errorMessage);
      throw errorMessage;
    }
    return json;
  });
}

export default loadMergeTokensOutputtedToken;
