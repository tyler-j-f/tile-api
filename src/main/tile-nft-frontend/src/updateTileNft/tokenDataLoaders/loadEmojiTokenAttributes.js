import {getTileEmojiUrlValue} from "../../view/metadataUpdatedTokenValueGetters/getTileEmojiValue";

export const loadEmojiTokenAttributes = ({
  tokenId,
  metadataToUpdate
}) => {

  const getRequestUrl = () =>
      `http://localhost:8080/api/image/emojiIndexes/get?${tokenId}?${getTileEmojiUrlValue(metadataToUpdate, 1)}&${getTileEmojiUrlValue(metadataToUpdate, 2)}&${getTileEmojiUrlValue(metadataToUpdate, 3)}&${getTileEmojiUrlValue(metadataToUpdate, 4)}`;

  return fetch(
      getRequestUrl(),
      {method: 'get'}
  )
  .then(response => {
    console.log("loadEmojiTokenAttributes getRequestUrl response found", response);
    if (response.status === 200) {
      return response.json();
    }
    return null;
  })
  .then(json => {
    console.log("loadEmojiTokenAttributes getRequestUrl json found", json);
    if (json === null) {
      let errorMessage = 'Token json is null';
      console.log(errorMessage);
      throw errorMessage;
    }
    return json
  })
  .catch(err => {
    console.log("Error caught!!!", err);
  });
}

export default loadEmojiTokenAttributes;
