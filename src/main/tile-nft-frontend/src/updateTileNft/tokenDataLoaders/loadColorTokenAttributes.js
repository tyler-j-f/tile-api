
export const loadColorTokenAttributes = ({
  tokenId,
  attributesRegex
}) => {
  const handleAttributesJson = (attributes) => {
    console.log('tiles/get attributes found. attributes: ', attributes);
    let filteredAttributes = attributes.filter(attribute => {
      return attribute.trait_type.match(attributesRegex);
    })
    console.log('Found color attributes:', filteredAttributes)
    return filteredAttributes;
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
    return handleAttributesJson(json.attributes);
  })
  .catch(err => {
    console.log("Error caught!!!", err);
  });
}

export default loadColorTokenAttributes;
