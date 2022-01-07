import {getTileColorUrlValue} from "../../etc/getTileColorValue";

const getEmojisMetadataUpdatedTokenUrl = (tokenId, metadataToUpdate) => {
  return `http://localhost:8080/api/image/metadataSet/get/${tokenId}?${getTileColorUrlValue(metadataToUpdate, 1)}&${getTileColorUrlValue(metadataToUpdate, 2)}&${getTileColorUrlValue(metadataToUpdate, 3)}&${getTileColorUrlValue(metadataToUpdate, 4)}`;
}

export default getEmojisMetadataUpdatedTokenUrl;