export const loadOpenSeaData = ({
  tokenId
}) => {

  const getOpenSeaTokenUrl = (tokenId) => `${window.location.origin}/api/contract/getOpenSeaTokenUrl/${tokenId}`

  const getOpenSeaSaleUrl = () => `${window.location.origin}/api/contract/getOpenSeaSaleUrl`

  return fetch(getOpenSeaTokenUrl(tokenId), {method: 'get'})
  .then(response => {
    if (response.status === 200) {
      return response.json();
    }
    throw "loadOpenSeaData getOpenSeaTokenUrl -> invalid response";
  })
  .then(getOpenSeaTokenUrlResult =>
    fetch(getOpenSeaSaleUrl(), {method: 'get'})
    .then(response => {
      if (response.status === 200) {
        return response.json();
      }
      throw "loadOpenSeaData getOpenSeaSaleUrl -> invalid response";
    })
    .then(
        getOpenSeaSaleUrlResult => {
          return {
            tokenUrl: getOpenSeaTokenUrlResult,
            saleUrl: getOpenSeaSaleUrlResult
          }
        }
    )
  )
  .catch(err => {
    console.log("loadTokenAttributes error caught!!!", err);
  });
}

export default loadOpenSeaData;
