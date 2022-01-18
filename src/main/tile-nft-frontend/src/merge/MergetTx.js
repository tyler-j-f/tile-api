import {useContractFunction} from "@usedapp/core";
import {useEffect, useState} from "react";
import {Button} from "react-bootstrap";
import { parseEther } from "@ethersproject/units";
import StyledText from "../styledComponents/StyledText";

const noop = () => {};

const MergeTx = ({contract, tokenId1, tokenId2, successCallback = noop}) => {
  const {state: mergeTxState, send } = useContractFunction(contract, 'merge', {value: parseEther('.1')});
  const [txStatus, setTxStatus] = useState({
    isLoading: false,
    errorText: ''
  });

  useEffect(() => {
    if (mergeTxState?.receipt) {
      successCallback(mergeTxState.receipt.transactionHash);
    }
  }, [mergeTxState]);

  function handleSendTx() {
    setTxStatus({...txStatus, isLoading: true, errorText: ''});
    send(tokenId1, tokenId2).then(response => {
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

export default MergeTx;
