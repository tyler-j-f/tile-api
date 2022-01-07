import TileContract from '../contractsJson/Tile.json'
import { ethers } from "ethers";
import {useEthers} from "@usedapp/core";
import {useEffect, useState} from "react";
import MetadataSetContract
  from "./MetadataSetContract";

const noop = () => {};

const MetadataSetContractWrapper = ({
  contractAddress = null,
  tokenId = null,
  metadataToUpdate = [],
  metadataToSetIndex,
  successCallback = noop,
  dataToSetGetter = noop,
  attributesRegex = '',
  numberOfEntriesToSet = 4}
) => {
  const { library: provider } = useEthers()
  const [tileContract, setTileContract] = useState(null);
  const [signer, setSigner] = useState(null);
  const [currentTokenAttributes, setCurrentTokenAttributes] = useState([]);

  useEffect(() => {loadTokenAttributes();}, []);

  const loadTokenAttributes = () => {
    fetch(`http://localhost:8080/api/tiles/get/${tokenId}`, {method: 'get'})
    .then(response => {
      if (response.status === 200) {
        return response.json();
      }
      return null;
    })
    .then(json => {
      if (json === null) {
        let errorMessage = 'Token json is null';
        console.log(errorMessage);
        throw errorMessage;
      }
      handleAttributesJson(json.attributes);
    })
    .then(() => {
      handleProviderAndSigner();
    })
    .catch(err => {
      console.log("Error caught!!!", err);
    });
  }

  const handleAttributesJson = (attributes) => {
    console.log('tiles/get attributes found. attributes: ', attributes);
    let filteredAttributes = attributes.filter(attribute => {
      const attributeRegex = attributesRegex;
      return attribute.trait_type.match(attributeRegex);
    })
    console.log('filteredAttributes:', filteredAttributes)
    setCurrentTokenAttributes(filteredAttributes);
  }

  const handleProviderAndSigner = () => {
    console.log('handleProviderAndSigner called');
    if (provider) {
      console.log('provider found called');
      setTileContract(
          new ethers.Contract(contractAddress, TileContract.abi, provider)
      );
      // The MetaMask plugin also allows signing transactions to
      // send ether and pay to change state within the blockchain.
      // For this, you need the account signer...
      setSigner(provider.getSigner());
    }
  }
  
  const getShouldRender = () => {
    return tileContract && signer && tokenId && currentTokenAttributes.length === numberOfEntriesToSet && metadataToUpdate.length === numberOfEntriesToSet
  }

  return (
      <>
        {getShouldRender() &&
          <MetadataSetContract
              contract={tileContract}
              tokenId={tokenId}
              dataToSetIndex={metadataToSetIndex}
              dataToSet={dataToSetGetter(metadataToUpdate, currentTokenAttributes)}
              successCallback={successCallback}
          />
        }
      </>
  );
}

export default MetadataSetContractWrapper;
