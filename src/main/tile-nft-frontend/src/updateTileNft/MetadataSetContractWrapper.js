import TileContract from '../contractsJson/Tile.json'
import { ethers } from "ethers";
import {useEthers} from "@usedapp/core";
import {useEffect, useState} from "react";
import MetadataSetContract
  from "./MetadataSetContract";
import {getTileRgbValue} from "../etc/getTileRgbValue";

const noop = () => {};

const MetadataSetContractWrapper = ({
  contractAddress,
  tokenId = null,
  metadataToSetIndex,
  metadataToUpdate = [],
  successCallback = noop,
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

  const getDataToSet = () => {
    const zeros = '0000000000000000000000000000'
    let output = `0x${getMetadataValueToSet(1)}${getMetadataValueToSet(2)}${getMetadataValueToSet(3)}${getMetadataValueToSet(4)}${zeros}`;
    console.log('dataToSet output', output);
    return output;
  }

  const getMetadataValueToSet = (index) => {
    return metadataToUpdate[index - 1] !== null ? getTileRgbValue(metadataToUpdate, index) : currentTokenAttributes[index - 1].value;
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
              dataToSet={getDataToSet()}
              successCallback={successCallback}
          />
        }
      </>
  );
}

export default MetadataSetContractWrapper;
