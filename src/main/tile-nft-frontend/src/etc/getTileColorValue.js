
export const getTileColorValue = (colorsToUpdate, tileNumber) => {
  let colorToUpdate = colorsToUpdate[tileNumber - 1];
  if (!colorToUpdate) {
    return '';
  }
  return `${getPixelSubRgbValue(colorToUpdate.r)}${getPixelSubRgbValue(colorToUpdate.g)}${getPixelSubRgbValue(colorToUpdate.b)}`;
}

export const getTileColorUrlValue = (colorsToUpdate, tileNumber) => {
  let valueBaseString = `tile${tileNumber}Color=`;
  return `${valueBaseString}${getTileColorValue(colorsToUpdate, tileNumber)}`;
}

const getPixelSubRgbValue = (subPixelValue) => {
  switch (subPixelValue.toString().length) {
    case 0:
      return null;
    case 1:
      return `00${subPixelValue}`;
    case 2:
      return `0${subPixelValue}`;
    case 3:
      return subPixelValue;
  }
}

export default getTileColorValue
