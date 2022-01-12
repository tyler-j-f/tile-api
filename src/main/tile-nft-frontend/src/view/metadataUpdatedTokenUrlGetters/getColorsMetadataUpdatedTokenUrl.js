import {getTileColorUrlValue} from "../metadataUpdatedTokenValueGetters/getTileColorValue";

const getColorsMetadataUpdatedTokenUrl = (tokenId, metadataToUpdate) => {
  return `${window.location.origin}/api/image/metadataSet/get/${tokenId}?${getTileColorUrlValue(metadataToUpdate, 1)}&${getTileColorUrlValue(metadataToUpdate, 2)}&${getTileColorUrlValue(metadataToUpdate, 3)}&${getTileColorUrlValue(metadataToUpdate, 4)}`;
}

export default getColorsMetadataUpdatedTokenUrl;