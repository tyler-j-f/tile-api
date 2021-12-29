import {
  ERC20Interface,
  useContractCall
} from "@usedapp/core";
import TileContract from '../contractsJson/Tile.json'
import { Interface } from '@ethersproject/abi'
import {Button} from "react-bootstrap";

function MetadataSetContractMethod() {

  const tokenIdToUpdate = 4;

  function useMetadataSet(
      contractAddress
  ) {
    const callResult =
    useContractCall({
      abi: new Interface(TileContract.abi),
      address: contractAddress,
      method: CONTRACT_METHOD_NAME,
      args: [
        tokenIdToUpdate, COLOR_METADATASET_INDEX, METADATA_TO_SET
      ]
      // args: getContractCallArgs({tokenId: tokenIdToUpdate, dataToSetIndex: COLOR_METADATASET_INDEX, rgbValue: METADATA_TO_SET})
    });
    console.log(callResult);
    return callResult
  }

  function testHandler() {
    console.log("DEBUGGING");
  }

  useMetadataSet(
      CONTRACT_ADDRESS
  );

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

export const CONTRACT_ADDRESS                      = "0xEc9547ABc4a8c24B99226BeE239c6E29814903Cd";
export const CURRENT_USER_ADDRESS_ADDRESS          = "0x4fdF8DF271e1A65B119D858eeA2A7681da8F9c15";
export const CONTRACT_METHOD_NAME                  = "metadataSet";
export const METADATA_TO_SET                       = "0x1210000550001112221110012220110220330000000000000000000000000000";
export const COLOR_METADATASET_INDEX               = 0;
export const EMOJI_METADATASET_INDEX               = 1;

export default MetadataSetContractMethod
