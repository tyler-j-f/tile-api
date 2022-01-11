import loadTokenMetadata from "./loadTokenMetadata";

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

  return loadTokenMetadata({tokenId})
  .then(json => {
    if (json === null) {
      let errorMessage = 'Token json is null';
      console.log(errorMessage);
      throw errorMessage;
    }
    return handleAttributesJson(json.attributes);
  })
  .catch(err => {
    console.log("loadColorTokenAttributes error caught!!!", err);
  });
}

export default loadColorTokenAttributes;
