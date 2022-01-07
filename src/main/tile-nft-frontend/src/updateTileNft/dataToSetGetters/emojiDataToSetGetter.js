import {getTileColorValue} from "../../etc/getTileColorValue";

const emojiDataToSetGetter = (metadataToUpdate, currentTokenAttributes) => {
  const zeros = '000000000000000000000000000000000000'
  const emojiOne = '0000100';
  const emojiTwo = '0000200';
  const emojiThree = '0000300';
  const emojiFour = '0000400';
  let output = `0x${emojiOne}${emojiTwo}${emojiThree}${emojiFour}${zeros}`;
  console.log('dataToSet output', output, metadataToUpdate, currentTokenAttributes);
  return output;
}

const getMetadataValueToSet = (metadataToUpdate, index, currentTokenAttributes) => {
  return metadataToUpdate[index - 1] !== null ? getTileColorValue(metadataToUpdate, index) : currentTokenAttributes[index - 1].value;
}

export default emojiDataToSetGetter;
