import React, {useState} from 'react'
import styled from 'styled-components';
import {Button} from "react-bootstrap";

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

const StyledText =
    styled.p`
    color: white;
    font-weight: bold;
    `;

export default TransactionSuccess
