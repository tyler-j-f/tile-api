import TileContract from '../contractsJson/Tile.json'
import { ethers } from "ethers";
import {useEthers} from "@usedapp/core";
import {useEffect, useState} from "react";
import MetadataSetContract
  from "./MetadataSetContract";

const MetadataSetContractWrapper = ({contractAddress, tokenId, dataToSetIndex, dataToSet}) => {
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
    setCurrentColorAttributes(currentColorAttributes);
  }

  const handleProviderAndSigner = () => {
    if (provider) {
      setTileContract(
          new ethers.Contract(contractAddress, TileContract.abi, provider)
      );
      // The MetaMask plugin also allows signing transactions to
      // send ether and pay to change state within the blockchain.
      // For this, you need the account signer...
      setSigner(provider.getSigner());
    }
  }

  return (
      <>
        {tileContract && signer && tokenId && <MetadataSetContract contract={tileContract} tokenId={tokenId} dataToSetIndex={dataToSetIndex} dataToSet={dataToSet} />}
      </>
  );
}

export default MetadataSetContractWrapper;
