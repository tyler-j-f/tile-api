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

  useEffect(() => {
    console.log("MetadataSetContractMethod useEffect.");
    if (provider) {
      console.log("Provider:")
      setTileContract(
          new ethers.Contract(CONTRACT_ADDRESS, TileContract.abi, provider)
      );
      console.log("tileContract:")
      console.log(tileContract);
    }
    return;
  }, []);

  return (
      <>
        <p>
          Getting the provider
        </p>
        {tileContract && <MetadataSetContractMethodInternal contract={tileContract} />}
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
