import {
  useChainCall, useEtherBalance, useEthers, ERC20Interface
} from "@usedapp/core";
import TileContract from '../contractsJson/Tile.json'
import { Interface } from '@ethersproject/abi'
import {Button} from "react-bootstrap";

function MetadataSetContractMethod() {

  const {activateBrowserWallet, account } = useEthers();
  const etherBalance = useEtherBalance(account);

  function handleConnectWallet() {
    activateBrowserWallet();
  }

  function useTokenAllowance(
      tokenAddress,
      ownerAddress
  ) {
    console.log("DEBUGGING hook");
    const balance =
    useChainCall(account && {
      address: tokenAddress,
      data: ERC20Interface.encodeFunctionData('balanceOf', [ownerAddress])
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

  return (
      <>
        <Button onClick={handleConnectWallet}>
          <p>
            Login
          </p>
        </Button>
        {account &&
          <Button onClick={testHandler}>
            <p>
              Test send a transaction
            </p>
          </Button>
        }
      </>
  );
}

export const CONTRACT_ADDRESS     = "0xEc9547ABc4a8c24B99226BeE239c6E29814903Cd";
export const OWNER_ADDRESS        = "0x4fdF8DF271e1A65B119D858eeA2A7681da8F9c15";
export const CONTRACT_METHOD_NAME = "metadataSet";

export default MetadataSetContractMethod
