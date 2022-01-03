import {Component, useState} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';

function TokenNumberForm() {

  const [value, setValue] = useState('');
  const [shouldShowImage, setShouldShowImage] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [isInvalidTokenNumber, setIsInvalidTokenNumber] = useState(false);
  const [isGeneralError, setIsGeneralError] = useState(false);
  const [imgValue, setImgValue] = useState('');

  const handleChange = (event) => {
    setValue(event.target.value);
  }

  const handleSubmit = (event) => {
    setIsLoading(true);
    loadTokenImage();
    event.preventDefault();
  }

  const loadTokenImage = () => {
    fetch(`http://localhost:8080/api/image/tile/get/${value}`, {method: 'get'})
    .then(response => {
      console.log(response);
      if (response.status === 200) {
        return response.blob();
      }
      if (response.status !== 200) {
        setShouldShowImage(false);
        setIsLoading(false);
        setIsInvalidTokenNumber(true);
        setIsGeneralError(false);
        setImgValue('');
      }
      return null;
    })
    .then(blob => {
      if (blob === null) {
        console.log('Image blob is null');
        return null;
      }
      setShouldShowImage(true);
      setIsLoading(false);
      setIsInvalidTokenNumber(false);
      setIsGeneralError(false);
      setImgValue(URL.createObjectURL(blob));
    })
    .catch(err => {
      setShouldShowImage(false);
      setIsLoading(false);
      setIsInvalidTokenNumber(false);
      setIsGeneralError(true);
      setImgValue('');
      console.log("Error caught!!!");
      console.log(err)
    });
  }

  const getGeneralErrorText = () => {
    return (
        <StyledErrorText>Error! Please try again later.</StyledErrorText>
    )
  }

  const getTokenNumberErrorText = () => {
    return (
        <StyledErrorText>Token number does not exist.</StyledErrorText>
    )
  }

  const getForm = () => {
    let formBody = getFormBody();
    return (
        <>
          <form onSubmit={handleSubmit}>
            <StyledLabel>
              Token Number:&nbsp;
              <input type="number" value={value} onChange={handleChange} />
            </StyledLabel>
            <input type="submit" value="Submit" />
            {formBody}
          </form>
        </>
    );
  }

  const getFormBody = () => {
    if (isGeneralError) {
      return getGeneralErrorText();
    }
    if (isInvalidTokenNumber) {
      return getTokenNumberErrorText();
    }
    return (
      <>
        {
          shouldShowImage && <StyledImg imgSource={imgValue} />
        }
      </>
    );
  }

  const getSpinner = () => {
    return (
        <Spinner animation="border" variant="primary" />
    );
  }

  return (
      isLoading ? getSpinner() : getForm()
  );

}

const StyledImg =
    styled.img.attrs(props => ({
      src: props.imgSource
    }))`
    width: 350px;
    height: 350px;
    margin: 10px;
    display: block
    `;

const StyledErrorText =
    styled.p`
      display: block;
      color: #FF4500;
      text-align: center;
      `;

const StyledLabel =
    styled.label`
      color: #F8F8FF;
      `;

export default TokenNumberForm;
