import {useEffect, useState} from 'react';
import Spinner from 'react-bootstrap/Spinner';
import loadViewTokenData from "./loadViewTokenData";
import StyledLabel from "../styledComponents/StyledLabel";
import StyledErrorText from "../styledComponents/StyledErrorText";
import {Button} from "react-bootstrap";
import StyledImage from "../styledComponents/StyledImage";

const noop = () => {};

const ViewToken = ({tokenLoadedCallback = noop, metadataToUpdate = [], getMetadataToUpdateTokenUrl = noop, enableUrlSearch = false, enableBlockExplorerLink = false, initialTokenId  = ''}) => {

  const [viewTokenData, setViewTokenData] = useState({
    tokenId: initialTokenId,
    isLoading: false,
    isInvalidTokenNumber: false,
    isGeneralError: false,
    imgValue: '',
    previouslyUsedMetadataToUpdate: []
  });

  useEffect(() => {
    if (enableUrlSearch && initialTokenId !== '') {
      loadViewTokenData({
        tokenId: initialTokenId, viewTokenData, setViewTokenData, tokenLoadedCallback, metadataToUpdate, getMetadataToUpdateTokenUrl, enableBlockExplorerLink
      });
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
    if (!!viewTokenData.tokenId) {
      setViewTokenData({...viewTokenData, isLoading: true})
      loadViewTokenData({
        tokenId: viewTokenData.tokenId, viewTokenData, setViewTokenData, tokenLoadedCallback, metadataToUpdate, getMetadataToUpdateTokenUrl, enableBlockExplorerLink
      });
    }
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
            <Button type='submit' value="Submit" className="styledButton" >
              <p>Submit</p>
            </Button>
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
        <StyledImage imgSource={viewTokenData.imgValue} />
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

export default ViewToken;
