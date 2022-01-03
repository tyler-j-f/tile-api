import TileContract from '../contractsJson/Tile.json'
import { ethers } from "ethers";
import {useEthers} from "@usedapp/core";
import {useEffect, useState} from "react";
import MetadataSetContractMethodInternal
  from "./MetadataSetContractMethodInternal";

function MetadataSetContractMethod() {
  const { library: provider } = useEthers()
  const [tileContract, setTileContract] = useState(null);
  const [signer, setSigner] = useState(null);

  useEffect(() => {
    if (provider) {
      setTileContract(
          new ethers.Contract(CONTRACT_ADDRESS, TileContract.abi, provider)
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
        {tileContract && signer && <MetadataSetContractMethodInternal contract={tileContract} />}
      </>
  );
}

export const CONTRACT_ADDRESS     = "0xEc9547ABc4a8c24B99226BeE239c6E29814903Cd";

export default MetadataSetContractMethod;
