import {PhotoshopPicker} from "react-color";
import {useState} from "react";

const noop = () => {};

const ColorSelector = ({onAccept = noop}) => {
  const [colorData, setColorData] = useState({hex: '#333333', r: '51', g: '51', b: '51' });

  const handleChange = (color, event) => {
    event.preventDefault();
    setColorData({
      hex: color.hex,
      r: color.rgb.r,
      g: color.rgb.g,
      b: color.rgb.b
    });
  };

  const handleChangeComplete = (color, event) => {
    event.preventDefault();
    setColorData({
      hex: color.hex,
      r: color.rgb.r,
      g: color.rgb.g,
      b: color.rgb.b
    });
  };

  const handleOnAccept = (event) => {
    console.log('handleOnAccept', event);
    event.preventDefault();
    onAccept(colorData);
  };

  return <PhotoshopPicker color={colorData} onAccept={handleOnAccept} onChange={handleChange} onChangeComplete={handleChangeComplete} />;
}

export default ColorSelector;
