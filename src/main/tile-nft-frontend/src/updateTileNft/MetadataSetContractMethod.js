
import {Button} from "react-bootstrap";
import TileContract from '../contractsJson/Tile.json'
import { ethers } from "ethers";
import {useEthers} from "@usedapp/core";
import {useEffect} from "react";

function MetadataSetContractMethod() {

  const { library: provider } = useEthers()

  useEffect(() => {
    console.log("useEffect.")
    if (provider) {
      console.log("Provider exists.")
      let contract = new ethers.Contract(CONTRACT_ADDRESS, TileContract.abi, provider);
      console.log(contract);
    }
    return;
  }, [provider]);

  return (
      <>
        <Button >
        </Button>
      </>
  );
}

export const CONTRACT_ADDRESS     = "0xEc9547ABc4a8c24B99226BeE239c6E29814903Cd";
export const OWNER_ADDRESS        = "0x4fdF8DF271e1A65B119D858eeA2A7681da8F9c15";
export const CONTRACT_METHOD_NAME = "metadataSet";

export default MetadataSetContractMethod;
