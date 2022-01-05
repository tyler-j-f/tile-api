
export const getTileRgbValue = (colorsToUpdate, tileNumber) => {
  let colorToUpdate = colorsToUpdate[tileNumber - 1];
  let valueBaseString = `tile${tileNumber}Color=`;
  if (!colorToUpdate) {
    return valueBaseString;
  }
  let rgbValue = `${getPixelSubRgbValue(colorToUpdate.r)}${getPixelSubRgbValue(colorToUpdate.g)}${getPixelSubRgbValue(colorToUpdate.b)}`;
  return `${valueBaseString}${rgbValue}`;
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


export default getTileRgbValue
