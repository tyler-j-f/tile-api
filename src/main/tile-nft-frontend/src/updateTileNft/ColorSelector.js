import {PhotoshopPicker} from "react-color";
import {useState} from "react";

const noop = () => {};

const ColorSelector = ({onAccept = noop}) => {
  const [hex, setHex] = useState('#333');

  const handleChange = (color, event) => {
    event.preventDefault();
    setHex(color.hex);
  };

  const handleChangeComplete = (color, event) => {
    event.preventDefault();
    setHex(color.hex);
  };

  const handleOnAccept = (event) => {
    console.log('handleOnAccept', event);
    event.preventDefault();
    onAccept(hex);
  };

  return <PhotoshopPicker color={hex} onAccept={handleOnAccept} onChange={handleChange} onChangeComplete={handleChangeComplete} />;
}

export default ColorSelector;
