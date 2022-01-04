import {useContractFunction, useEthers} from "@usedapp/core";
import {useEffect} from "react";
import styled from "styled-components";
import {Button} from "react-bootstrap";
import { parseEther } from "@ethersproject/units";

const MetadataSetContract = ({contract, tokenId, dataToSetIndex, dataToSet}) => {
  const { state, send } = useContractFunction(contract, 'metadataSet', {value: parseEther('.1')});

  function handleSendTx() {
    send(tokenId, dataToSetIndex, dataToSet).then(response => {
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

export default MetadataSetContract;
