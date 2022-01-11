import loadTokenMetadata from "./loadTokenMetadata";

export const loadTokenAttributes = ({
  tokenId
}) => {
  return loadTokenMetadata({tokenId})
  .then(json => {
    if (json === null) {
      let errorMessage = 'Token json is null';
      console.log(errorMessage);
      throw errorMessage;
    }
    return json.attributes;
  })
  .catch(err => {
    console.log("loadTokenAttributes error caught!!!", err);
  });
}

export default loadTokenAttributes;
