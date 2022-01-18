import {useContractFunction} from "@usedapp/core";
import {useEffect, useState} from "react";
import {Button} from "react-bootstrap";
import { parseEther } from "@ethersproject/units";
import StyledText from "../styledComponents/StyledText";
import StyledErrorText from "../styledComponents/StyledErrorText";

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
        <Button onClick={handleSendTx} active={txStatus.isLoading} disabled={txStatus.isLoading} className="styledButton" >
          <p>Send Transaction</p>
        </Button>
        {txStatus.isLoading &&
          <StyledText>Sending Transaction...</StyledText>
        }
        {txStatus.errorText !== '' &&
          <StyledErrorText>{txStatus.errorText}</StyledErrorText>
        }
      </>
  );
}

export default MetadataSetTx;
