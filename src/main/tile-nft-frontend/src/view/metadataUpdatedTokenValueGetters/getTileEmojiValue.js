
export const getTileEmojiUrlValue = (emojisToUpdate, emojiNumber) => {
  let valueBaseString = `emoji${emojiNumber}Color=`;
  return `${valueBaseString}${getEmojiColorValue(emojisToUpdate, emojiNumber)}`;
}

export const getEmojiColorValue = (emojisToUpdate, emojiNumber) => {
  let emojiToUpdate = emojisToUpdate[emojiNumber - 1];
  if (!emojiToUpdate) {
    return '';
  }
  return `${getEmojiValueWithPaddedZeros(emojiToUpdate)}`;
}

const getEmojiValueWithPaddedZeros = (emojiValue) => {
  switch (emojiValue.toString().length) {
    case 0:
      return `0000000`;
    case 1:
      return `000000${emojiValue}`;
    case 2:
      return `00000${emojiValue}`;
    case 3:
      return `0000${emojiValue}`;
    case 4:
      return `000${emojiValue}`;
    case 5:
      return `00${emojiValue}`;
    case 6:
      return `0${emojiValue}`;
    case 7:
      return `${emojiValue}`;
  }
}

export default getEmojiColorValue
