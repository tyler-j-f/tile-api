import {
  ERC20Interface,
  useContractCall
} from "@usedapp/core";
import TileContract from '../contractsJson/Tile.json'
import { Interface } from '@ethersproject/abi'
import {Button} from "react-bootstrap";

function MetadataSetContractMethod() {

  function useTokenAllowance(
      tokenAddress,
      ownerAddress
  ) {
    console.log("DEBUGGING hook");
    const balance =
    useContractCall({
      abi: new Interface(TileContract.abi),
      address: tokenAddress,
      method: 'balanceOf',
      args: [ownerAddress],
    });
    console.log("DEBUGGING balance");
    console.log(balance);
    return balance
  }

  function testHandler() {
    console.log("DEBUGGING");
  }

  const test = useTokenAllowance(
      CONTRACT_ADDRESS,
      OWNER_ADDRESS
  );

  function getAbiTextFromFile() {
    return fetch('../../../resources/contractsJson/Tile.json')
    .then(response => response.text())
  }

  function getContractCallArgs({tokenId, dataToSetIndex, rgbValue}) {
    return [
      tokenId, dataToSetIndex, rgbValue
    ];
  }

  return (
      <Button onClick={testHandler}>
        <p>
          Test send a transaction
        </p>
      </Button>
  );
}

export const CONTRACT_ADDRESS     = "0xEc9547ABc4a8c24B99226BeE239c6E29814903Cd";
export const OWNER_ADDRESS        = "0x4fdF8DF271e1A65B119D858eeA2A7681da8F9c15";
export const CONTRACT_METHOD_NAME = "metadataSet";

export default MetadataSetContractMethod
