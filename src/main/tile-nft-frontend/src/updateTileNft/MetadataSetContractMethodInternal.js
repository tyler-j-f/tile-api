import {useContractFunction, useEthers} from "@usedapp/core";
import {useEffect} from "react";
import styled from "styled-components";
import {Button} from "react-bootstrap";
import { parseEther } from "@ethersproject/units";

const MetadataSetContractMethodInternal = ({contract}) => {
  const { state, send } = useContractFunction(contract, 'metadataSet', {value: parseEther('.1')});

  function handleSendTx() {
    send(10, 1, METADATA).then(response => {
      console.log("response found!!!", response, state);
    }).catch(e => {
      console.log("ERROR CAUGHT!!!", e);
    });
  }

  return (
      <Button onClick={handleSendTx}>
        Send Transaction
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
export const METADATA = "0x0000111000022200003330000444000000000000000000000000000000000000";


export default MetadataSetContractMethodInternal;
