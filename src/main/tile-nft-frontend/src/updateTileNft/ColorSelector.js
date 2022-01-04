import {PhotoshopPicker} from "react-color";
import {useEffect, useState} from "react";

const ColorSelector = () => {
  const [hex, setHex] = useState('#333');

  useEffect(() => {
    console.log('useEffect', hex);
    return;
  }, [hex]);

  const handleChange = (color, event) => {
    event.preventDefault();
    setHex(color.hex);
  };

  const handleChangeComplete = (color, event) => {
    event.preventDefault();
    setHex(color.hex);
  };

  return <PhotoshopPicker color={hex} onChange={handleChange} onChangeComplete={handleChangeComplete} />;
}

export default ColorSelector;
