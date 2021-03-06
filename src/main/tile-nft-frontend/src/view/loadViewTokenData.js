import loadBlockExplorerUrl from "./loadBlockExplorerUrl";
import loadContractAddress from "./loadContractAddress";

const noop = () => {};

const loadViewTokenData = ({
  tokenId = '', viewTokenData  = null, setViewTokenData = noop, tokenLoadedCallback = noop, metadataToUpdate = [], getMetadataToUpdateTokenUrl = noop, enableBlockExplorerLink = false
}) => {

  const getUrl = (tokenId) => {
    if (metadataToUpdate.length === 0) {
      return `${window.location.origin}/api/image/tile/get/${tokenId}`;
    }
    return getMetadataToUpdateTokenUrl(tokenId, metadataToUpdate);
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

  return loadTokenImageData(tokenId).then(
      loadTokenImageDataResult => {
        loadContractAddress().then(
            contractAddress => {
              let dataToSet = {
                ...viewTokenData,
                ...loadTokenImageDataResult
              };
              let dataToCallback = {
                tokenId: tokenId,
                contractAddress: contractAddress,
                isInvalidTokenNumber: loadTokenImageDataResult.isInvalidTokenNumber
              };
              if (!enableBlockExplorerLink) {
                setViewTokenData(dataToSet);
                tokenLoadedCallback(dataToCallback);
              }
              return {
                dataToSet,
                dataToCallback
              };
            }
        ).then(({
          dataToSet,
            dataToCallback
        }) => {
          if (enableBlockExplorerLink) {
            loadBlockExplorerUrl(tokenId).then(
                response => {
                  console.log("ViewToken url response", response);
                  setViewTokenData(dataToSet);
                  tokenLoadedCallback({
                    ...dataToCallback,
                    blockExplorerUrl: response
                  });
                }
            )
          }
        });
      }
  );
}

export default loadViewTokenData;