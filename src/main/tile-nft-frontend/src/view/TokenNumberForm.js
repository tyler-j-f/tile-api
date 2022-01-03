import {Component, useState} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';

function TokenNumberForm() {

  const [viewTokenData, setViewTokenData] = useState({
    value: '',
    isLoading: false,
    isInvalidTokenNumber: false,
    isGeneralError: false,
    imgValue: ''
  });

  const handleChange = (event) => {
    setViewTokenData({...viewTokenData, value: event.target.value})
  }

  const handleSubmit = (event) => {
    setViewTokenData({...viewTokenData, isLoading: true})
    loadTokenImage();
    event.preventDefault();
  }

  const loadTokenImage = () => {
    fetch(`http://localhost:8080/api/image/tile/get/${viewTokenData.value}`, {method: 'get'})
    .then(response => {
      console.log("loadTokenImage response", response);
      if (response.status === 200) {
        return response.blob();
      }
      if (response.status !== 200) {
        setViewTokenData({
          ...viewTokenData,
          isLoading: false,
          isInvalidTokenNumber: true,
          isGeneralError: false,
          imgValue: ''
        })
      }
      return null;
    })
    .then(blob => {
      if (blob === null) {
        console.log('Image blob is null');
        return null;
      }
      setViewTokenData({
        ...viewTokenData,
        isLoading: false,
        isInvalidTokenNumber: false,
        isGeneralError: false,
        imgValue: URL.createObjectURL(blob)
      })
    })
    .catch(err => {
      setViewTokenData({
        ...viewTokenData,
        isLoading: false,
        isInvalidTokenNumber: false,
        isGeneralError: true,
        imgValue: ''
      })
      console.log("Error caught!!!", err);
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
              <input type="number" value={viewTokenData.value} onChange={handleChange} />
            </StyledLabel>
            <input type="submit" value="Submit" />
            {formBody}
          </form>
        </>
    );
  }

  const getFormBody = () => {
    if (viewTokenData.isGeneralError) {
      return getGeneralErrorText();
    }
    if (viewTokenData.isInvalidTokenNumber) {
      return getTokenNumberErrorText();
    }
    return (
      <>
        {
          viewTokenData.imgValue !== '' && <StyledImg imgSource={viewTokenData.imgValue} />
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
      viewTokenData.isLoading ? getSpinner() : getForm()
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
