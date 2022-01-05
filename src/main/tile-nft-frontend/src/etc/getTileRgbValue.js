
export const getTileRgbValue = (colorsToUpdate, tileNumber) => {
  let colorToUpdate = colorsToUpdate[tileNumber - 1];
  if (!colorToUpdate) {
    return '';
  }
  return `${getPixelSubRgbValue(colorToUpdate.r)}${getPixelSubRgbValue(colorToUpdate.g)}${getPixelSubRgbValue(colorToUpdate.b)}`;
}

export const getTileRgbUrlValue = (colorsToUpdate, tileNumber) => {
  let valueBaseString = `tile${tileNumber}Color=`;
  return `${valueBaseString}${getTileRgbValue(colorsToUpdate, tileNumber)}`;
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

1030080081640591180021531411030080080000000000000000000000000000

export default getTileRgbValue
