import {useContractFunction, useEthers} from "@usedapp/core";
import {useEffect, useState} from "react";
import styled from "styled-components";
import {Button} from "react-bootstrap";
import { parseEther } from "@ethersproject/units";
import StyledText from "../styledComponents/StyledText";

const noop = () => {};

const MetadataSetTx = ({contract, tokenId, dataToSetIndex, dataToSet, successCallback = noop}) => {
  const {state: matadataSetTxState, send } = useContractFunction(contract, 'metadataSet', {value: parseEther('.1')});
  const [txStatus, setTxStatus] = useState({
    isLoading: false,
    errorText: ''
  });

  useEffect(() => {
    if (matadataSetTxState?.receipt) {
      successCallback(matadataSetTxState.receipt.transactionHash);
    }
  }, [matadataSetTxState]);

  function handleSendTx() {
    setTxStatus({...txStatus, isLoading: true, errorText: ''});
    send(tokenId, dataToSetIndex, dataToSet).then(response => {
      setTxStatus({...txStatus, isLoading: false});
    }).catch(e => {
      console.log("ERROR CAUGHT!!!", e);
      setTxStatus({...txStatus, isLoading: false, errorText: 'There was en error sending the transaction! Please try again later.'});
    });
  }

  return (
      <>
        <Button onClick={handleSendTx} active={txStatus.isLoading} disabled={txStatus.isLoading}>
          Send Transaction
        </Button>
        {txStatus.isLoading &&
        <StyledText>Sending Transaction...</StyledText>
        }
        {txStatus.errorText !== '' &&
        <StyledText>{txStatus.errorText}</StyledText>
        }
      </>
  );
}

export default MetadataSetTx;
