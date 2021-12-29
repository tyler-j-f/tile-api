import {
  ERC20Interface,
  useSendTransaction, useEtherBalance, useEthers
} from "@usedapp/core";
import TileContract from '../contractsJson/Tile.json'
import { Interface } from '@ethersproject/abi'
import {Button} from "react-bootstrap";

function MetadataSetContractMethod() {
  const {activateBrowserWallet, account } = useEthers();
  const etherBalance = useEtherBalance(account);
  const { sendTransaction, state } = useSendTransaction();

  function handleConnectWallet() {
    activateBrowserWallet();
  }

  function handleSendTransactionClick(e) {
    e.preventDefault();
    // let data = sendTransaction({
    //   to: '0x273d7D3E390885cc5c68Def05F6D7ef5B78b6D57',
    //   from: '0x4fdF8DF271e1A65B119D858eeA2A7681da8F9c15',
    //   from: 'metadataSet',
    //   args: [1, 0, '0x2550000000001530000000001532552042040000000000000000000000000000'],
    // });
    // console.log(data);
    console.log(sendTransaction);
    console.log(state);
    return null;
  }

  function testHandler() {
    console.log("DEBUGGING");
  }

  return (
      <>
        <Button onClick={handleConnectWallet}>
          <p>
            Login
          </p>
        </Button>
        {account && etherBalance && (
            <Button onClick={handleSendTransactionClick}>
              <p>
                Test send a transaction
              </p>
            </Button>
        )}
      </>
  );
}

export const CONTRACT_ADDRESS     = "0x273d7D3E390885cc5c68Def05F6D7ef5B78b6D57";
export const OWNER_ADDRESS        = "0x4fdF8DF271e1A65B119D858eeA2A7681da8F9c15";
export const CONTRACT_METHOD_NAME = "metadataSet";

export default MetadataSetContractMethod
