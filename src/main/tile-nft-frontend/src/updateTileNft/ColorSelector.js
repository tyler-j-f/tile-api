import {PhotoshopPicker} from "react-color";
import {useState} from "react";

const noop = () => {};

const ColorSelector = ({onAccept = noop}) => {
  const [color, setColor] = useState('#333');

  const handleChange = (color, event) => {
    event.preventDefault();
    setColor(color);
  };

  const handleChangeComplete = (color, event) => {
    event.preventDefault();
    setColor(color);
  };

  const handleOnAccept = (event) => {
    console.log('handleOnAccept', event);
    event.preventDefault();
    onAccept(color);
  };

  return <PhotoshopPicker color={color} onAccept={handleOnAccept} onChange={handleChange} onChangeComplete={handleChangeComplete} />;
}

export default ColorSelector;
