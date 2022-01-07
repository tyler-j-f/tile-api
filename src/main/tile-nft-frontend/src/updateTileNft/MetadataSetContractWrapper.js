import TileContract from '../contractsJson/Tile.json'
import {useEthers} from "@usedapp/core";
import {useEffect, useState} from "react";
import MetadataSetContract
  from "./MetadataSetContract";
import {ethers} from "ethers";

const noop = () => {};

const MetadataSetContractWrapper = ({
  contractAddress = null,
  tokenId = null,
  metadataToUpdate = [],
  metadataToSetIndex,
  successCallback = noop,
  dataToSetGetter = noop,
  loadTokenAttributes = noop,
  attributesRegex = '',
  numberOfEntriesToSet = 4
}
) => {
  const { library: provider } = useEthers()
  const [tileContract, setTileContract] = useState(null);
  const [signer, setSigner] = useState(null);
  const [currentTokenAttributes, setCurrentTokenAttributes] = useState([]);

  useEffect(() => {loadTokenAttributes({
    tokenId,
    handleProviderAndSigner,
    attributesRegex,
    setCurrentTokenAttributes
  });}, []);
  
  const getShouldRender = () => {
    return tileContract && signer && tokenId && currentTokenAttributes.length === numberOfEntriesToSet && metadataToUpdate.length === numberOfEntriesToSet
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
