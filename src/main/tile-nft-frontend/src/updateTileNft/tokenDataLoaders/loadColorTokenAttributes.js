
export const loadColorTokenAttributes = ({
  tokenId,
  handleProviderAndSigner,
  handleAttributesJson
}) => {

  return fetch(`http://localhost:8080/api/tiles/get/${tokenId}`, {method: 'get'})
  .then(response => {
    if (response.status === 200) {
      return response.json();
    }
    return null;
  })
  .then(json => {
    if (json === null) {
      let errorMessage = 'Token json is null';
      console.log(errorMessage);
      throw errorMessage;
    }
    handleAttributesJson(json.attributes);
  })
  .then(() => {
    handleProviderAndSigner();
  })
  .catch(err => {
    console.log("Error caught!!!", err);
  });
}

export default loadColorTokenAttributes;
