import {useEffect, useState} from 'react';
import styled from 'styled-components';
import Spinner from 'react-bootstrap/Spinner';

const noop = () => {};

const ViewToken = ({tokenLoadedCallback = noop, metadataToUpdate = [], getMetadataToUpdateTokenUrl = noop, enableUrlSearch = false}) => {

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
        loadTokenImage(tokenId);
      }
    }
  }, []);

  useEffect(() => {
    if (viewTokenData?.tokenId !== '' && metadataToUpdate?.length !== viewTokenData?.previouslyUsedMetadataToUpdate?.length) {
      setViewTokenData({...viewTokenData, isLoading: true})
      loadTokenImage(viewTokenData?.tokenId);
    }
  }, [metadataToUpdate]);

  const handleChange = (event) => {
    setViewTokenData({...viewTokenData, tokenId: event.target.value})
  }

  const handleSubmit = (event) => {
    setViewTokenData({...viewTokenData, isLoading: true})
    loadTokenImage(viewTokenData?.tokenId);
    event.preventDefault();
  }

  const getUrl = (tokenId) => {
    if (metadataToUpdate.length === 0) {
      return `${window.location.origin}/api/image/tile/get/${tokenId}`;
    }
    return getMetadataToUpdateTokenUrl(viewTokenData.tokenId, metadataToUpdate);
  }

  const loadTokenImage = (tokenId) => {
    loadTokenImageData(tokenId).then(
        loadTokenImageDataResult => {
          loadContractAddress().then(
              contractAddress => {
                setViewTokenData(loadTokenImageDataResult);
                tokenLoadedCallback({
                  tokenId: tokenId,
                  contractAddress: contractAddress,
                  isInvalidTokenNumber: loadTokenImageDataResult.isInvalidTokenNumber
                });
              }
          )
        }
    )
  }

  const loadContractAddress = () => {
    return fetch(
        `${window.location.origin}/api/contract/getAddress`,
        {method: 'get'}
    )
    .then(response => {
      if (response.status === 200) {
        return response.json();
      }
      return null;
    })
    .then(json => {
      console.log("ViewToken loadContractAddress json found", json);
      if (json === null) {
        let errorMessage = 'loadContractAddress json is null';
        console.log(errorMessage);
        throw errorMessage;
      }
      return json
    })
    .catch(err => {
      console.log("Error caught!!!", err);
    });
  }


  const loadTokenImageData = (tokenId) => {
    return fetch(getUrl(tokenId), {method: 'get'})
    .then(response => {
      if (response.status === 200) {
        return response.blob();
      }
      if (response.status === 404) {
        return {
          ...viewTokenData,
          isLoading: false,
          isInvalidTokenNumber: true,
          isGeneralError: false,
          imgValue: ''
        };
      }
      throw "Error: Unrecognized response."
    })
    .then(response => {
      console.log("loadTokenImageData then. response: ", response)
      if (response === null) {
        console.log('Image blob is null');
        return null;
      }
      if (response.isInvalidTokenNumber) {
        console.log('Invalid token number requested.');
        return response;
      }
      // Otherwise return response.blob(); was called above.
      return {
        ...viewTokenData,
        isLoading: false,
        isInvalidTokenNumber: false,
        isGeneralError: false,
        imgValue: URL.createObjectURL(response),
        previouslyUsedMetadataToUpdate: metadataToUpdate
      };
    })
    .catch(err => {
      console.log("Error caught!!!", err);
      return {
        ...viewTokenData,
        isLoading: false,
        isInvalidTokenNumber: false,
        isGeneralError: true,
        imgValue: ''
      };
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
      (!viewTokenData || viewTokenData.isLoading) ? getSpinner() : getForm()
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
