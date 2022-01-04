import {PhotoshopPicker} from "react-color";
import {useEffect, useState} from "react";

const ColorSelector = () => {
  const [background, setBackground] = useState('#fff');

  useEffect(() => {
    console.log(background);
    return;
  }, []);

  const handleChange = (color, event) => {
    console.log('handleChange', event, color);
    setBackground(color.hex);
  };

  const handleChangeComplete = (color, event) => {
    console.log('handleChangeComplete', event, color);
    setBackground(color.hex);
  };


  return <PhotoshopPicker onChange={handleChange} onChangeComplete={handleChangeComplete} />;
}

export default ColorSelector;
