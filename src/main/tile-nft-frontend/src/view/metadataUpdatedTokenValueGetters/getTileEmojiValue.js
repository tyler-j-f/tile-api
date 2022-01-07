
export const getTileEmojiUrlValue = (emojisToUpdate, emojiNumber) => {
  let valueBaseString = `tile${emojiNumber}Emoji=`;
  return `${valueBaseString}${getEmojiColorValue(emojisToUpdate, emojiNumber)}`;
}

export const getEmojiColorValue = (emojisToUpdate, emojiNumber, shouldPadWithZeros = false) => {
  let emojiToUpdate = emojisToUpdate[emojiNumber - 1];
  if (!emojiToUpdate) {
    return '';
  }
  return shouldPadWithZeros ? `${getEmojiValueWithPaddedZeros(emojiToUpdate)}` : emojiToUpdate;
}

const getEmojiValueWithPaddedZeros = (emojiIndex) => {
  switch (emojiIndex.toString().length) {
    case 0:
      return `0000000`;
    case 1:
      return `000000${emojiIndex}`;
    case 2:
      return `00000${emojiIndex}`;
    case 3:
      return `0000${emojiIndex}`;
    case 4:
      return `000${emojiIndex}`;
    case 5:
      return `00${emojiIndex}`;
    case 6:
      return `0${emojiIndex}`;
    case 7:
      return `${emojiIndex}`;
  }
}

export default getEmojiColorValue
