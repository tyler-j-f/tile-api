import {getTileEmojiUrlValue} from "../metadataUpdatedTokenValueGetters/getTileEmojiValue";

const getEmojisMetadataUpdatedTokenUrl = (tokenId, metadataToUpdate) => {
  return `${window.location.origin}/api/image/metadataSet/get/${tokenId}?${getTileEmojiUrlValue(metadataToUpdate, 1)}&${getTileEmojiUrlValue(metadataToUpdate, 2)}&${getTileEmojiUrlValue(metadataToUpdate, 3)}&${getTileEmojiUrlValue(metadataToUpdate, 4)}`;
}

export default getEmojisMetadataUpdatedTokenUrl;