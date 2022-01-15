const noop = () => {};

const loadBlockExplorerUrl = ({
  tokenId = '', viewTokenData  = null, setViewTokenData = noop, tokenLoadedCallback = noop, metadataToUpdate = [], getMetadataToUpdateTokenUrl = noop
}) => {

  const loadUrl = () => {
    return fetch(
        `${window.location.origin}/api/tiles/blockExplorerUrl/get/${tokenId}`,
        {method: 'get'}
    )
    .then(response => {
      if (response.status === 200) {
        return response.json();
      }
      return null;
    })
    .then(json => {
      console.log("loadBlockExplorerUrl loadUrl json found", json);
      if (json === null) {
        let errorMessage = 'loadBlockExplorerUrl loadUrl json is null';
        console.log(errorMessage);
        throw errorMessage;
      }
      return json
    })
    .catch(err => {
      console.log("Error caught!!!", err);
    });
  }

  return loadUrl().then(
      response => {
        console.log("Debug loadBlockExplorerUrl response", response)
      }
  );
}

export default loadBlockExplorerUrl;