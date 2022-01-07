import {getTileRgbUrlValue} from "../../etc/getTileRgbValue";

const getColorsMetadataUpdatedTokenUrl = (tokenId, metadataToUpdate) => {
  return `http://localhost:8080/api/image/metadataSet/get/${tokenId}?${getTileRgbUrlValue(metadataToUpdate, 1)}&${getTileRgbUrlValue(metadataToUpdate, 2)}&${getTileRgbUrlValue(metadataToUpdate, 3)}&${getTileRgbUrlValue(metadataToUpdate, 4)}`;
}

export default getColorsMetadataUpdatedTokenUrl;