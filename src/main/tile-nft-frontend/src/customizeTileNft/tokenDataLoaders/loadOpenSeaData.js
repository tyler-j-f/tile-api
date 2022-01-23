export const loadOpenSeaData = ({
  tokenId
}) => {

  const getOpenSeaTokenPath = (tokenId) => `${window.location.origin}/api/contract/getOpenSeaTokenUrl/${tokenId}`

  const getOpenSeaSalePath = () => `${window.location.origin}/api/contract/getOpenSeaSaleUrl`

  const getOpenSeaCollectionPath = () => `${window.location.origin}/api/contract/getOpenSeaCollectionUrl`

  const getTokenUrl = () => fetch(getOpenSeaTokenPath(tokenId), {method: 'get'})
  .then(response => {
    if (response.status === 200) {
      return response.json();
    }
    throw "loadOpenSeaData getOpenSeaTokenPath -> invalid response";
  });

  const getSaleUrl = () => fetch(getOpenSeaSalePath(), {method: 'get'})
  .then(response => {
    if (response.status === 200) {
      return response.json();
    }
    throw "loadOpenSeaData getOpenSeaSalePath -> invalid response";
  });

  const getCollectionUrl = () => fetch(getOpenSeaCollectionPath(), {method: 'get'})
  .then(response => {
    if (response.status === 200) {
      return response.json();
    }
    throw "loadOpenSeaData getCollectionUrl -> invalid response";
  });

  return getTokenUrl().then(getOpenSeaTokenUrlResult =>
    getSaleUrl().then(getOpenSeaSaleUrlResult =>
        getCollectionUrl().then(getOpenSeaCollectionUrlResult => ({
        tokenUrl: getOpenSeaTokenUrlResult,
        saleUrl: getOpenSeaSaleUrlResult,
        collectionUrl: getOpenSeaCollectionUrlResult
      }))
    )
  )
  .catch(err => {
    console.log("loadTokenAttributes error caught!!!", err);
    return {};
  });
}

export default loadOpenSeaData;
