
export const loadTokenMetadata = ({
  tokenId
}) => {
  return fetch(`${window.location.origin}/api/tiles/get/${tokenId}`, {method: 'get'})
  .then(response => {
    if (response.status === 200) {
      return response.json();
    }
    return null;
  })
  .then(json => {
    if (json === null) {
      let errorMessage = 'loadTokenMetadata response is null';
      console.log(errorMessage);
      throw errorMessage;
    }
    return json;
  });
}

export default loadTokenMetadata;
