import TileContract from '../contractsJson/Tile.json'
import { ethers } from "ethers";
import {useEthers} from "@usedapp/core";
import {useEffect, useState} from "react";
import styled from "styled-components";
import MetadataSetContractMethodInternal
  from "./MetadataSetContractMethodInternal";

function MetadataSetContractMethod() {
  const { library: provider } = useEthers()
  const [tileContract, setTileContract] = useState(null);
  const [signer, setSigner] = useState(null);

  useEffect(() => {
    if (provider) {
      let contractFound = new ethers.Contract(CONTRACT_ADDRESS, TileContract.abi, provider);
      setTileContract(
          contractFound
      );
      // The MetaMask plugin also allows signing transactions to
      // send ether and pay to change state within the blockchain.
      // For this, you need the account signer...
      const signer = provider.getSigner();
      setSigner(signer);
    }
    return;
  }, []);

  return (
      <>
        <p>
          Getting the provider
        </p>
        {tileContract && signer && <MetadataSetContractMethodInternal contract={tileContract} signer={signer} />}
      </>
  );
}

const StyledButton = styled.button`
    margin: 5px;
    padding: 5px;
`;

export const CONTRACT_ADDRESS     = "0xEc9547ABc4a8c24B99226BeE239c6E29814903Cd";
export const OWNER_ADDRESS        = "0x4fdF8DF271e1A65B119D858eeA2A7681da8F9c15";
export const CONTRACT_METHOD_NAME = "metadataSet";

export default MetadataSetContractMethod;
