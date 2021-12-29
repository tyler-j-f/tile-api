import {useContractCall, useTokenAllowance} from "@usedapp/core";
import {Button} from "react-bootstrap";

function MetadataSetContractMethod() {

  function useTokenAllowance(
      tokenAddress,
      ownerAddress,
      spenderAddress
  ) {
    const [allowance] =
    useContractCall(
        ownerAddress &&
        spenderAddress &&
        tokenAddress && {
          abi: null,
          address: tokenAddress,
          method: 'allowance',
          args: [ownerAddress, spenderAddress],
        }
    ) ?? []
    return allowance
  }

  function testHandler() {
    console.log("DEBUGGING");
  }

  const test = useTokenAllowance(
      CONTRACT_ADDRESS,
      OWNER_ADDRESS,
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
