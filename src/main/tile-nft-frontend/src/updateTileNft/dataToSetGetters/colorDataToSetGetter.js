import {getTileColorValue} from "../../etc/getTileColorValue";

const colorDataToSetGetter = (metadataToUpdate, currentTokenAttributes) => {
  const zeros = '0000000000000000000000000000'
  let output = `0x${getMetadataValueToSet(metadataToUpdate, 1, currentTokenAttributes)}${getMetadataValueToSet(metadataToUpdate, 2, currentTokenAttributes)}${getMetadataValueToSet(metadataToUpdate, 3, currentTokenAttributes)}${getMetadataValueToSet(metadataToUpdate, 4, currentTokenAttributes)}${zeros}`;
  console.log('dataToSet output', output);
  return output;
}

const getMetadataValueToSet = (metadataToUpdate, index, currentTokenAttributes) => {
  return metadataToUpdate[index - 1] !== null ? getTileColorValue(metadataToUpdate, index) : currentTokenAttributes[index - 1].value;
}

export default colorDataToSetGetter;
