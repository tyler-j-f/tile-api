import {useContractFunction, useEthers} from "@usedapp/core";
import {useEffect} from "react";
import styled from "styled-components";
import {Button} from "react-bootstrap";

const MetadataSetContractMethodInternal = ({contract}) => {
  const { state, send } = useContractFunction(contract, 'balanceOf');

  useEffect(() => {
    console.log("MetadataSetContractMethodInternal useEffect.");
    console.log("state");
    console.log(state);
    console.log("send");
    console.log(send);
    return;
  }, []);

  function handleSendTx() {
    console.log("handleSendTx");
    send({value: OWNER_ADDRESS}).then(response => {
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

  return (
      <Button onClick={handleSendTx}>
        Send Tx
      </Button>
  );
}

const StyledButton = styled.button`
    margin: 5px;
    padding: 5px;
`;

export const CONTRACT_ADDRESS     = "0xEc9547ABc4a8c24B99226BeE239c6E29814903Cd";
export const OWNER_ADDRESS        = "0x4fdF8DF271e1A65B119D858eeA2A7681da8F9c15";
export const CONTRACT_METHOD_NAME = "metadataSet";

export default MetadataSetContractMethodInternal;
