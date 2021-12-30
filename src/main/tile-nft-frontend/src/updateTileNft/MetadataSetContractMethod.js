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
      // The DAI Contract is currently connected to the Provider,
      // which is read-only. You need to connect to a Signer, so
      // that you can pay to send state-changing transactions.
      const tileContractWithSigner = contract.connect(signer);
      console.log(tileContractWithSigner);
      // Each DAI has 18 decimal places
      // const dai = ethers.utils.parseUnits("1.0", 18);
      // Send 1 DAI to "ricmoo.firefly.eth"
      tileContractWithSigner.name().then(result => {
            console.log("name");
            console.log(result);
            return tileContractWithSigner.symbol();
          }
      ).then(result2 => {
            console.log("symbol");
            console.log(result2);
            return tileContractWithSigner.balanceOf(OWNER_ADDRESS);
          }
      ).then(result3 => {
            console.log("balanceOf");
            console.log(result3);
            return true;
          }
      )
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
