import React from 'react'
import {Button} from "react-bootstrap";
import StyledText from "../styledComponents/StyledText";

const TransactionSuccess = ({txId, handleSendAnotherTx, subText}) => {

  return (
      <>
        <StyledText>
          Success!!! Transaction Id: <a href={`https://rinkeby.etherscan.io/tx/${txId}`} >{txId}</a>
        </StyledText>
        <StyledText>{subText}</StyledText>
        <Button onClick={handleSendAnotherTx}>
          Send another update transaction?
        </Button>
      </>
  )
}

export default TransactionSuccess
