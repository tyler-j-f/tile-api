import getTileEmojiValue
  from "../../view/metadataUpdatedTokenValueGetters/getTileEmojiValue";

const emojiDataToSetGetter = (metadataToUpdate, currentTokenAttributes) => {
  const zeros = '000000000000000000000000000000000000'
  let output = `0x${getMetadataValueToSet(metadataToUpdate, 1, currentTokenAttributes)}${getMetadataValueToSet(metadataToUpdate, 2, currentTokenAttributes)}${getMetadataValueToSet(metadataToUpdate, 3, currentTokenAttributes)}${getMetadataValueToSet(metadataToUpdate, 4, currentTokenAttributes)}${zeros}`;
  console.log('emojiDataToSetGetter output', output);
  return output;
}

const getMetadataValueToSet = (metadataToUpdate, index, currentTokenAttributes) => {
  return metadataToUpdate[index - 1] !== null ? getTileEmojiValue(metadataToUpdate, index) : currentTokenAttributes[index - 1].value;
}

export default emojiDataToSetGetter;
