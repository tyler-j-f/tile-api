import getTileColorValue
  from "../../view/metadataUpdatedTokenValueGetters/getTileColorValue";

const colorDataToSetGetter = (metadataToUpdate, currentColorAttributes) => {
  const zeros = '0000000000000000000000000000'
  let output = `0x${getMetadataValueToSet(metadataToUpdate, 1, currentColorAttributes)}${getMetadataValueToSet(metadataToUpdate, 2, currentColorAttributes)}${getMetadataValueToSet(metadataToUpdate, 3, currentColorAttributes)}${getMetadataValueToSet(metadataToUpdate, 4, currentColorAttributes)}${zeros}`;
  console.log('colorDataToSetGetter output', output);
  return output;
}

const getMetadataValueToSet = (metadataToUpdate, index, currentColorAttributes) => {
  return metadataToUpdate[index - 1] !== null ? getTileColorValue(metadataToUpdate, index) : currentColorAttributes[index - 1].value;
}

export default colorDataToSetGetter;
