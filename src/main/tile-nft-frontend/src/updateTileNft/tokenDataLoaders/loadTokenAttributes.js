import loadTokenMetadata from "./loadTokenMetadata";

export const loadTokenAttributes = ({
  tokenId
}) => {
  console.log("debug tokenId 2", tokenId);
  return loadTokenMetadata({tokenId})
  .then(result => {
    if (result === null) {
      let errorMessage = 'Token json is null';
      console.log(errorMessage);
      throw errorMessage;
    }
    return result.attributes;
  })
  .catch(err => {
    console.log("loadTokenAttributes error caught!!!", err);
  });
}

export default loadTokenAttributes;
