import {getTileEmojiIndexValue} from "../../view/metadataUpdatedTokenValueGetters/getTileEmojiValue";

const emojiDataToSetGetter = (metadataToUpdate, emojiIndexes) => {
  const zeros = '000000000000000000000000000000000000'
  let output = `0x${getTileEmojiIndexValue(emojiIndexes, 1)}${getTileEmojiIndexValue(emojiIndexes, 2)}${getTileEmojiIndexValue(emojiIndexes, 3)}${getTileEmojiIndexValue(emojiIndexes, 4)}${zeros}`;
  console.log('emojiDataToSetGetter output', output);
  return output;
}

export default emojiDataToSetGetter;
