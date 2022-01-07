
export const loadColorTokenAttributes = ({
  tokenId,
  handleProviderAndSigner,
  attributesRegex,
  setCurrentTokenAttributes
}) => {

  const handleAttributesJson = (attributes) => {
    console.log('tiles/get attributes found. attributes: ', attributes);
    let filteredAttributes = attributes.filter(attribute => {
      return attribute.trait_type.match(attributesRegex);
    })
    console.log('filteredAttributes:', filteredAttributes)
    setCurrentTokenAttributes(filteredAttributes);
  }

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
