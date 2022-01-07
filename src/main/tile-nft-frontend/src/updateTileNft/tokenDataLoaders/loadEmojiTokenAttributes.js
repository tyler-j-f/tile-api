import loadColorTokenAttributes from "./loadColorTokenAttributes";

export const loadEmojiTokenAttributes = ({
  tokenId,
  attributesRegex
}) => {
  return loadColorTokenAttributes({
    tokenId,
    attributesRegex
  }).then(result => {
    console.log("loadEmojiTokenAttributes result found: ", result);
    return result;
  });
}

export default loadEmojiTokenAttributes;
