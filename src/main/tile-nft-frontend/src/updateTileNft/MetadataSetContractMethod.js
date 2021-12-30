import TileContract from '../contractsJson/Tile.json'
import { ethers } from "ethers";
import {useEthers} from "@usedapp/core";
import {useEffect} from "react";
import styled from "styled-components";

function MetadataSetContractMethod() {
  const { library: provider } = useEthers()


  function handleMetadataSet() {
    console.log("handleMetadataSet");
  }

  useEffect(() => {
    console.log("useEffect.")
    if (provider) {
      console.log("Provider exists.")
      let contract = new ethers.Contract(CONTRACT_ADDRESS, TileContract.abi, provider);
      console.log(contract);
      // If you don't specify a //url//, Ethers connects to the default
      // (i.e. ``http:/\/localhost:8545``)
      const provider = new ethers.providers.JsonRpcProvider();
      console.log(provider);
      // The provider also allows signing transactions to
      // send ether and pay to change state within the blockchain.
      // For this, we need the account signer...
      const signer = provider.getSigner();
      console.log(signer);
    }
    return;
  }, []);

  return (
      <>
        <StyledButton onClick={handleMetadataSet}>
          metadataSet
        </StyledButton>
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
