import {useEffect, useState} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';
import loadViewTokenData from "./loadViewTokenData";
import StyledLabel from "../styledComponents/StyledLabel";
import StyledErrorText from "../styledComponents/StyledErrorText";
import StyledText from "../styledComponents/StyledText";

const noop = () => {};

const ViewToken = ({tokenLoadedCallback = noop, metadataToUpdate = [], getMetadataToUpdateTokenUrl = noop, enableUrlSearch = false, enableBlockExplorerLink = false}) => {

  const [viewTokenData, setViewTokenData] = useState({
    tokenId: '',
    isLoading: false,
    isInvalidTokenNumber: false,
    isGeneralError: false,
    imgValue: '',
    previouslyUsedMetadataToUpdate: []
  });

  useEffect(() => {
    if (enableUrlSearch) {
      let tokenId = new URL(window.location.href).searchParams.get('tokenId');
      if (tokenId) {
        setViewTokenData({
          ...viewTokenData,
          tokenId,
          isLoading: true
        })
        loadViewTokenData({
          tokenId, viewTokenData, setViewTokenData, tokenLoadedCallback, metadataToUpdate, getMetadataToUpdateTokenUrl, enableBlockExplorerLink
        });
      }
    }
  }, []);

  useEffect(() => {
    if (viewTokenData?.tokenId !== '' && metadataToUpdate?.length !== viewTokenData?.previouslyUsedMetadataToUpdate?.length) {
      setViewTokenData({...viewTokenData, isLoading: true})
      loadViewTokenData({
        tokenId: viewTokenData.tokenId, viewTokenData, setViewTokenData, tokenLoadedCallback, metadataToUpdate, getMetadataToUpdateTokenUrl, enableBlockExplorerLink
      });
    }
  }, [metadataToUpdate]);

  const handleChange = (event) => {
    setViewTokenData({...viewTokenData, tokenId: event.target.value})
  }

  const handleSubmit = (event) => {
    setViewTokenData({...viewTokenData, isLoading: true})
    loadViewTokenData({
      tokenId: viewTokenData.tokenId, viewTokenData, setViewTokenData, tokenLoadedCallback, metadataToUpdate, getMetadataToUpdateTokenUrl, enableBlockExplorerLink
    });
    event.preventDefault();
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
            <StyledInput type="submit" value="Submit" />
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
    return viewTokenData.imgValue !== '' && (
        <StyledImg imgSource={viewTokenData.imgValue} />
    );
  }

  const getSpinner = () => {
    return (
        <Spinner animation="border" variant="primary" />
    );
  }

  return (
      (!viewTokenData || viewTokenData.isLoading) ? getSpinner() : getForm()
  );

}

const StyledInput =
    styled.input`
    position: absolute;
    margin-left: 5px;
    `;

const StyledImg =
    styled.img.attrs(props => ({
      src: props.imgSource
    }))`
    width: 350px;
    height: 350px;
    margin: 10px;
    display: block
    `;

export default ViewToken;
