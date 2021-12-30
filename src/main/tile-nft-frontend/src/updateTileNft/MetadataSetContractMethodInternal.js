import {useContractFunction, useEthers} from "@usedapp/core";
import {useEffect} from "react";
import styled from "styled-components";
import {Button} from "react-bootstrap";
import { parseEther } from "@ethersproject/units";

const MetadataSetContractMethodInternal = ({contract, signer}) => {
  const { state, send } = useContractFunction(contract, 'metadataSet', {value: parseEther('.1')});

  useEffect(() => {
    console.log("contract");
    console.log(contract);
    console.log("MetadataSetContractMethodInternal useEffect.");
    console.log("state");
    console.log(state);
    console.log("send");
    console.log(send);
    return;
  }, []);

  function handleSendTx() {
    console.log("handleSendTx");
    send(1, 0, METADATA).then(response => {
      console.log("response found!!!");
      console.log(response);
      console.log("response state");
      console.log(state);
      console.log("response send");
      console.log(send);
    }).catch(e => {
      console.log("ERROR CAUGHT!!!");
      console.log(e);
    });
  }

  function handlePrint() {
    console.log(state);
  }

  return (
      <>
        <Button onClick={handleSendTx}>
          Send Tx
        </Button>
        <Button onClick={handlePrint}>
          Print Result
        </Button>
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
export const METADATA = "0x2550000000001530000000001532552042040000000000000000000000000000";

export default MetadataSetContractMethodInternal;
