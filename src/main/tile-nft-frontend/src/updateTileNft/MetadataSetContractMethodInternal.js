import {useContractFunction, useEthers} from "@usedapp/core";
import {useEffect} from "react";
import styled from "styled-components";

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

  return (
      <p>
        useContractFunction
      </p>
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
