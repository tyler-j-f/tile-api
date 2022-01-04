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

  useEffect(() => {
    if (provider) {
      setTileContract(
          new ethers.Contract(contractAddress, TileContract.abi, provider)
      );
      // The MetaMask plugin also allows signing transactions to
      // send ether and pay to change state within the blockchain.
      // For this, you need the account signer...
      setSigner(provider.getSigner());
    }
    return;
  }, []);

  return (
      <>
        {tileContract && signer && tokenId && <MetadataSetContract contract={tileContract} tokenId={tokenId} dataToSetIndex={dataToSetIndex} dataToSet={dataToSet} />}
      </>
  );
}

export default MetadataSetContractWrapper;
