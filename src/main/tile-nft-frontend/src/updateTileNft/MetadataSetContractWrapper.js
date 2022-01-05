import TileContract from '../contractsJson/Tile.json'
import { ethers } from "ethers";
import {useEthers} from "@usedapp/core";
import {useEffect, useState} from "react";
import MetadataSetContract
  from "./MetadataSetContract";
import {getTileRgbValue} from "../etc/getTileRgbValue";

const NUMBER_OF_COLORS_TO_SET = 4;

const MetadataSetContractWrapper = ({contractAddress, tokenId, dataToSetIndex, colorsToUpdate = []}) => {
  const { library: provider } = useEthers()
  const [tileContract, setTileContract] = useState(null);
  const [signer, setSigner] = useState(null);
  const [currentColorAttributes, setCurrentColorAttributes] = useState([]);

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
      const tileColorRegex = /Tile \d Color/;
      return attribute.trait_type.match(tileColorRegex);
    })
    console.log('filteredAttributes:', filteredAttributes)
    setCurrentColorAttributes(filteredAttributes);
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
    let output = `0x${getTileColorDataToSet(1)}${getTileColorDataToSet(2)}${getTileColorDataToSet(3)}${getTileColorDataToSet(4)}${zeros}`;
    console.log('dataToSet output', output);
    return output;
  }

  const getTileColorDataToSet = (tileNumber) => {
    return colorsToUpdate[tileNumber - 1] !== null ? getTileRgbValue(colorsToUpdate, tileNumber) : currentColorAttributes[tileNumber - 1].value;
  }
  
  const getShouldRender = () => {
    return tileContract && signer && tokenId && currentColorAttributes.length === NUMBER_OF_COLORS_TO_SET && colorsToUpdate.length === NUMBER_OF_COLORS_TO_SET
  }

  return (
      <>
        {getShouldRender() &&
          <MetadataSetContract
              contract={tileContract}
              tokenId={tokenId}
              dataToSetIndex={dataToSetIndex}
              dataToSet={getDataToSet()}
          />
        }
      </>
  );
}

export default MetadataSetContractWrapper;
