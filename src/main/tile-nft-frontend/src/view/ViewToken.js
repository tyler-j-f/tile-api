import {Component, useEffect, useState} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';

const noop = () => {};

const ViewToken = ({tokenLoadedCallback = noop, colorsToUpdate = [], emojisToUpdate = []}) => {

  const [viewTokenData, setViewTokenData] = useState({
    tokenId: '',
    isLoading: false,
    isInvalidTokenNumber: false,
    isGeneralError: false,
    imgValue: ''
  });

  useEffect(() => {
    if (colorsToUpdate.length > 0 || emojisToUpdate.length > 0) {
      //loadTokenImage();
    }
  }, [colorsToUpdate, emojisToUpdate]);

  const handleChange = (event) => {
    setViewTokenData({...viewTokenData, tokenId: event.target.value})
  }

  const handleSubmit = (event) => {
    setViewTokenData({...viewTokenData, isLoading: true})
    loadTokenImage();
    event.preventDefault();
  }

  const getUrl = () => {
    console.log("getUrl", colorsToUpdate, colorsToUpdate.length)
    if (colorsToUpdate.length === 0) {
      return `http://localhost:8080/api/image/tile/get/${viewTokenData.tokenId}`;
    }
    let url = `http://localhost:8080/api/image/metadataSet/get/${viewTokenData.tokenId}?${getColorRequestValue(colorsToUpdate, 1)}&${getColorRequestValue(colorsToUpdate, 2)}&${getColorRequestValue(colorsToUpdate, 3)}&${getColorRequestValue(colorsToUpdate, 4)}`;
    console.log('getUrl', url);
    return url;
  }

  const getColorRequestValue = (colorsToUpdate, tileNumber) => {
    let colorValue = colorsToUpdate[tileNumber - 1] != null ? stripHashTagFromHexColorValue(colorsToUpdate[tileNumber - 1]) : '';
    return `tile${tileNumber}Color=${colorValue}`;
  }

  const stripHashTagFromHexColorValue = (hexColorValue) => {
    return hexColorValue.substring(1, hexColorValue.length);
  }

  const loadTokenImage = () => {
    fetch(getUrl(), {method: 'get'})
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
      tokenLoadedCallback(viewTokenData.tokenId);
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
              <input type="number" value={viewTokenData.tokenId} onChange={handleChange} />
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

export default ViewToken;
