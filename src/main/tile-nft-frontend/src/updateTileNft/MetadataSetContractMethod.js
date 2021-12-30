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
      let contractFound = new ethers.Contract(CONTRACT_ADDRESS, TileContract.abi, provider);
      setTileContract(
          contractFound
      );
      console.log("tileContract:")
      console.log(contractFound);
      // A Web3Provider wraps a standard Web3 provider, which is
      // what MetaMask injects as window.ethereum into each page
      const provider = new ethers.providers.Web3Provider(window.ethereum)
      // The MetaMask plugin also allows signing transactions to
      // send ether and pay to change state within the blockchain.
      // For this, you need the account signer...
      const signer = provider.getSigner();
      console.log('signer');
      console.log(signer);
      console.log('signer address');
      console.log(signer._address);
      signer.getAddress().then(result => {
        console.log("Get Address");
        console.log(result);
      })
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
