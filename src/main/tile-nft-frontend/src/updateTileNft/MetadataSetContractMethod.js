import { useEthers, useContractCall} from "@usedapp/core";
import { ethers } from "ethers";
import {Button} from "react-bootstrap";

function MetadataSetContractMethod() {

  function useTestContractCall() {
    getAbiTextFromFile().then(fileText => {
      console.log(fileText);
      let contractJson = JSON.parse(fileText);
      console.log(contractJson);
      let abiJson = JSON.stringify(contractJson.abi);
      console.log(abiJson);
      const simpleContractInterface = new ethers.utils.Interface(abiJson);
      let test = useContractCall({
        abi: simpleContractInterface,
        address: CONTRACT_ADDRESS,
        method: CONTRACT_METHOD_NAME,
        args: getContractCallArgs({
          tokenId: 4,
          dataToSetIndex: 0,
          rgbValue: "0x121000055000111222111001222011022033"
        }),
      }) ?? [];
      console.log(test);
    });
  }

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
      <Button onClick={useTestContractCall}>
        <p>
          Test send a transaction
        </p>
      </Button>
  );

}

export const CONTRACT_ADDRESS     = "0xEc9547ABc4a8c24B99226BeE239c6E29814903Cd";
export const CONTRACT_METHOD_NAME = "metadataSet";

export default MetadataSetContractMethod
